package com.simple.todolist.view.screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.simple.todolist.view.viewmodel.TodoViewModel

@Composable
fun HomeScreen(application: Application) {

    var showDialog by remember { mutableStateOf(false) }
    val todoViewModel by remember { mutableStateOf(TodoViewModel(application)) }

    Scaffold(topBar = {
        TopAppBar(backgroundColor = MaterialTheme.colors.primaryVariant, elevation = 3.dp) {
            Text(
                text = "Simple Todo List",
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                style = MaterialTheme.typography.h6
            )
        }
    }, floatingActionButton = {
        FloatingActionButton(onClick = { showDialog = true }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Icon Add")
            if (showDialog) {
                InputDialog(
                    showDialog = true,
                    onDismiss = { showDialog = false },
                    onConfirm = { dataTodo ->

                        todoViewModel.insertTodo(dataTodo)
                        showDialog = false
                    }
                )
            }
        }
    }) {
        Column(
            modifier = Modifier
                .padding(it.calculateBottomPadding())
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TodoListScreen(todoViewModel)
        }
    }
}