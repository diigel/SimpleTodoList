package com.simple.todolist.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.simple.todolist.data.local.DataTodo
import com.simple.todolist.data.local.TodoDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao = TodoDatabase.getDatabase(application).todoDao()

    private val _todoList = MutableStateFlow<List<DataTodo>>(emptyList())
    val todoList: StateFlow<List<DataTodo>> = _todoList

    init {
        // Launch a coroutine to observe the todoDao.getAllTodos() flow
        // and update the value of _allTodos whenever there's a change
        viewModelScope.launch {
            todoDao.getAllTodo().collect { dataTodoList ->
                _todoList.value = dataTodoList
                Log.d("","todo list is -> $dataTodoList")
            }
        }
    }

    // Function to insert a new Todo
    fun insertTodo(dataTodo: DataTodo) {
        viewModelScope.launch {
            todoDao.insert(dataTodo)
        }
    }

    // Function to update an existing Todo
    fun updateTodo(dataTodo: DataTodo) {
        viewModelScope.launch {
            todoDao.update(dataTodo)
        }
    }

    // Function to delete a Todo
    fun deleteTodo(dataTodo: DataTodo) {
        viewModelScope.launch {
            todoDao.delete(dataTodo)
        }
    }
}