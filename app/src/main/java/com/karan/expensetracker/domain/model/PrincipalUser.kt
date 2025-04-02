package com.karan.expensetracker.domain.model

data class PrincipalUser(
    private val userId : String,
    private val username : String,
    private val firstName : String,
    private val lastName : String,
    private val email : String,
    private val phoneNumber: Long
)
