package com.helic.eisec.model.task

class TaskBuilder {
    var title: String = ""
    var description: String = ""
    var category: String = ""
    var emoji: String = ""
    var urgency: Int = 0
    var importance: Int = 0
    var priority: Priority = Priority.IMPORTANT
    var due: String = ""
    var isCompleted: Boolean = false
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()
    var id: Int = 0

    fun build(): Task = Task(
        title,
        description,
        category,
        urgency,
        importance,
        priority,
        due,
        isCompleted,
        createdAt,
        updatedAt,
        id
    )
}