package com.simple.todolist.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dataTodo: DataTodo)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(dataTodo: DataTodo)

    @Delete
    suspend fun delete(dataTodo: DataTodo)

    @Query("SELECT * FROM todo")
    fun getAllTodo(): Flow<List<DataTodo>>

}