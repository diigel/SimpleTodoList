package com.simple.todolist.common

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

fun String?.convertStringToDate(inputFormat : String? = "yyyyMMdd",outputFormat :String? = "yyyy-MM-dd"): String {
    if (this == null) return ""

    val inputDateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
    val outputDateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())

    val date = inputDateFormat.parse(this)
    return date?.let { outputDateFormat.format(it) }.orEmpty()
}

fun Context.showToast(message : String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}