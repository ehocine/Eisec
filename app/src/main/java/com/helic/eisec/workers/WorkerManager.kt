package com.helic.eisec.workers

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.helic.eisec.model.task.Task
import com.helic.eisec.utils.getCalendar
import java.util.concurrent.TimeUnit


fun Context.scheduleReminders(task: Task) {
    val calendar = getCalendar(task.due)
    val initialDelay =
        (calendar.timeInMillis) - System.currentTimeMillis()
    if (initialDelay > 0) {
        val data = Data.Builder()
            .putInt(ARG_ID, task.id)
            .build()

        val worker = OneTimeWorkRequest.Builder(ReminderWorker::class.java)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()
        WorkManager.getInstance(this)
            .enqueueUniqueWork(task.getWorkerId(), ExistingWorkPolicy.REPLACE, worker)
    } else {
        cancelReminder(task)
    }
}

fun Context.cancelReminder(task: Task) {
    WorkManager.getInstance(this).cancelUniqueWork(task.getWorkerId())
}