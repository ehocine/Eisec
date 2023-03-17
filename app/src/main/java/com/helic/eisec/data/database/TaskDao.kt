package com.helic.eisec.data.database

import androidx.room.*
import com.helic.eisec.model.task.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getAllTask(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Query("DELETE FROM task where id =:id")
    suspend fun deleteTaskByID(id: Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: Task)

    @Query("SELECT * FROM task where id=:id")
    fun findByID(id: Int): Flow<Task>

    @Query("SELECT * FROM task where id=:id")
    fun findTaskByID(id: Int): Task

    @Query("UPDATE task set isCompleted= :isCompleted where id=:id")
    suspend fun updateTaskStatus(id: Int, isCompleted: Boolean)

    @Query("SELECT * FROM task WHERE priority=:priority")
    fun getTaskByPriority(priority: String): Flow<List<Task>>

    @Query("SELECT COUNT(id) FROM task WHERE priority=:priority")
    fun getTaskByPriorityCount(priority: String): Flow<Int>
}