package com.helic.eisec.model.task

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "category")
    val category: String = "",
    @ColumnInfo(name = "urgency")
    val urgency: Int = 0,
    @ColumnInfo(name = "importance")
    val importance: Int = 0,
    @ColumnInfo(name = "priority")
    val priority: Priority = Priority.IMPORTANT,
    @ColumnInfo(name = "timer")
    val due: String = "",
    @ColumnInfo(name = "isCompleted")
    val isCompleted: Boolean,
    @ColumnInfo(name = "createdAt")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updatedAt")
    val updatedAt: Long = System.currentTimeMillis(),
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0
) {
    fun getWorkerId() = "reminder_$id"
}

enum class Priority {
    URGENT,
    IMPORTANT,
    DELEGATE,
    DUMP
}

fun task(block: TaskBuilder.() -> Unit): Task = TaskBuilder().apply(block).build()