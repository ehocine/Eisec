package com.helic.eisec.repository

import com.helic.eisec.data.database.TaskDao
import com.helic.eisec.model.task.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository @Inject constructor(
    private val taskDao: TaskDao
) {

    /**
     * Get a all Task.
     */
    fun getAllTask(): Flow<List<Task>> =
        taskDao.getAllTask().flowOn(Dispatchers.IO).conflate()

    /**
     * Create a new Task.
     * @param task
     */
    suspend fun insert(task: Task) = taskDao.insertTask(task)

    /**
     * Update a existing Task.
     * @param task
     */
    suspend fun update(task: Task) = taskDao.updateTask(task)

    /**
     * Delete a [Task].
     * @param id
     */
    suspend fun delete(id: Int) = taskDao.deleteTaskByID(id)

    /**
     * Find a Task by it's [ID].
     * @param id
     */

    fun find(id: Int) = taskDao.findByID(id).flowOn(Dispatchers.IO).conflate()

    /**
     * Update a status for a Task by it's [ID].
     * @param id
     * @param isCompleted
     */
    suspend fun updateStatus(id: Int, isCompleted: Boolean) =
        taskDao.updateTaskStatus(id = id, isCompleted = isCompleted)

    /**
     * Get a Task by it's [Priority].
     * @param priority
     */
    fun getTaskByPriority(priority: String): Flow<List<Task>> =
        taskDao.getTaskByPriority(priority).flowOn(Dispatchers.IO).conflate()

    /**
     * Get a Task count by it's [Priority].
     * @param priority
     */
    fun getTaskByPriorityCount(priority: String): Flow<Int> =
        taskDao.getTaskByPriorityCount(priority).flowOn(Dispatchers.IO).conflate()
}