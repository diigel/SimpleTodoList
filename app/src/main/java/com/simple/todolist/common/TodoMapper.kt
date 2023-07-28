package com.simple.todolist.common

import com.simple.todolist.data.local.DataTodo

object TodoMapper {

    fun mapToDataTodo(id : Long,title : String, description : String, dueDate : String) : DataTodo {
        return DataTodo(
            id = id,
            title = title,
            description = description,
            dueDate = dueDate
        )
    }
}