package com.simple.todolist.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.simple.todolist.common.TodoMapper
import com.simple.todolist.common.convertStringToDate
import com.simple.todolist.common.showToast
import com.simple.todolist.data.local.DataTodo

@Composable
fun InputDialog(
    showDialog: Boolean,
    isEditedTodo: DataTodo? = null,
    onDismiss: () -> Unit,
    onConfirm: (DataTodo) -> Unit
) {
    var title by remember { mutableStateOf(isEditedTodo?.title ?: "") }
    var description by remember { mutableStateOf(isEditedTodo?.description ?: "") }
    var dueDate by remember { mutableStateOf(isEditedTodo?.dueDate.convertStringToDate(outputFormat = "yyyyMMdd")) }
    val context = LocalContext.current
    val pattern = remember { Regex("^[0-9]*\$") }

    if (showDialog) {
        Dialog(onDismissRequest = { onDismiss() }) {
            AlertDialog(
                modifier = Modifier.padding(8.dp),
                onDismissRequest = { onDismiss() },
                title = { Text(text = "Input Dialog", modifier = Modifier.padding(bottom = 8.dp)) },
                text = {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Title") }
                        )
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Description") }
                        )

                        OutlinedTextField(
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            value = dueDate,
                            onValueChange = {
                                if (it.length <= 8 ) {
                                    dueDate = when (it.toDoubleOrNull()) {
                                        null -> dueDate
                                        else -> it
                                    }
                                }
                            },
                            label = { Text("Due Date : yyyyMMdd") }
                        )
                    }

                },
                confirmButton = {
                    TextButton(onClick = {
                        if (dueDate.isNotEmpty()){
                            onConfirm(TodoMapper.mapToDataTodo(
                                id = isEditedTodo?.id ?: 0,
                                title = title,
                                description = description,
                                dueDate = dueDate.convertStringToDate()
                            ))
                            onDismiss()
                        }else {
                           context.showToast("Due Date is required !")
                        }
                    }) {
                        Text("Submit")
                    }
                }
            )
        }
    }
}
