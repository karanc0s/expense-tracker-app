package com.karan.expensetracker.data.repository

import com.karan.expensetracker.domain.repository.TokenRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class TokenRepositoryIMPL @Inject constructor() : TokenRepository {

    override suspend fun validateToken(token: String): Result<*> {
        delay(5000L)
        return Result.success(true)
    }

    override suspend fun refreshToken(token: String): Result<*> {
        delay(5000L)
        return Result.success(true)
    }

}