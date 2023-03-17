package com.helic.eisec.workers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.helic.eisec.R
import com.helic.eisec.data.database.TaskDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


const val ARG_ID = "arg_id"

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParams: WorkerParameters,
    private val taskDao: TaskDao,
    private val notificationManager: NotificationManager
) :
    CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val reminderId = workerParams.inputData.getInt(ARG_ID, -1)
        if (reminderId == -1) {
            return Result.failure()
        }

        withContext(Dispatchers.IO) {

            val task = taskDao.findTaskByID(reminderId)

            val channel = NotificationChannel(
                context.getString(R.string.reminder_channel_id),
                context.getString(R.string.reminder_channel_name),
                NotificationManager.IMPORTANCE_HIGH,
            )
            channel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            channel.enableVibration(true)
            channel.setBypassDnd(true)
            notificationManager.createNotificationChannel(channel)

            val builder =
                NotificationCompat.Builder(context, context.getString(R.string.reminder_channel_id))
                    .apply {
                        setContentTitle(task.title)
                        setSmallIcon(R.drawable.eisec_logo)
                        setCategory(NotificationCompat.CATEGORY_ALARM)
                        setContentText(task.description)
                        setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        setDefaults(Notification.DEFAULT_SOUND)
                        setDefaults(Notification.DEFAULT_VIBRATE)
                    }
            builder.priority = NotificationCompat.PRIORITY_MAX
            builder.setAutoCancel(true)

            notificationManager.notify(task.id, builder.build())
        }
        return Result.success()
    }
}