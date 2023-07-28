package com.simple.todolist.view.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simple.todolist.data.local.DataTodo
import com.simple.todolist.view.viewmodel.TodoViewModel

@Composable
fun TodoListScreen(todoViewModel: TodoViewModel) {

    val todoList by todoViewModel.todoList.collectAsState(emptyList())

    if (todoList.isEmpty()) {
        Text(
            text = "Nothing Todo, please create todo first.",
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            textAlign = TextAlign.Center
        )
    } else {
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            items(todoList) { dataTodo ->
                 ItemTodo(dataTodo = dataTodo,todoViewModel)
            }
        }
    }

}

@Composable
fun ItemTodo(dataTodo: DataTodo, todoViewModel: TodoViewModel) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = dataTodo.title,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = dataTodo.description,
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Due Date: ${dataTodo.dueDate}",
                style = TextStyle(fontSize = 14.sp, fontStyle = FontStyle.Italic)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { showDialog = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color.Black
                    )
                }
                IconButton(
                    onClick = {
                        todoViewModel.deleteTodo(dataTodo)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Black
                    )
                }

                if (showDialog) {
                    InputDialog(
                        showDialog = true,
                        isEditedTodo = dataTodo,
                        onDismiss = { showDialog = false },
                        onConfirm = { dataTodo ->
                            todoViewModel.updateTodo(dataTodo)
                            showDialog = false
                        }
                    )
                }
            }
        }
    }
}

