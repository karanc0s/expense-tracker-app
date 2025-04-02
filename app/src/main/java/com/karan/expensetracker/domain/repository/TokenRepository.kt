package com.karan.expensetracker.domain.repository

interface TokenRepository {

    suspend fun validateToken(token : String) : Result<*>

    suspend fun refreshToken(token : String) : Result<*>

}