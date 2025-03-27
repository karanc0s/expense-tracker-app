package com.karan.expensetracker.models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Message(
    val from : String,
    val message : String,
    val timestamp : Long
)

fun Long.parsedDate(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd/MMM/yyyy HH:mm", Locale.getDefault())
    return format.format(date)
}
