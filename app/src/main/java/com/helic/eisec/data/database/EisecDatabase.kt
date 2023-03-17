package com.helic.eisec.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.helic.eisec.model.task.Task

@Database(entities = [Task::class], version = 3, exportSchema = false)
abstract class EisecDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
}