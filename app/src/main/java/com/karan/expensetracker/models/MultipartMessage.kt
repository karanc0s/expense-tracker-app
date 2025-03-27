package com.karan.expensetracker.models

data class MultipartMessage(
    val from : String,
    val message : String,
    val data : ByteArray
)
