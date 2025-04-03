package com.karan.expensetracker.data.repository

import com.karan.expensetracker.domain.model.PrincipalUser
import com.karan.expensetracker.domain.repository.UserRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserRepositoryIMPL @Inject constructor() : UserRepository {



    override suspend fun logIn(
        name: String,
        password: String
    ): Result<PrincipalUser> {
        delay(5000L)
        return Result.success(
            PrincipalUser(
                userId = "123456789",
                username = name,
                email = "$name@gmail.com",
                firstName = "FirstName",
                lastName = "LastName",
                phoneNumber = 9090909090L
            )
        )
    }

    override suspend fun signUp(
        name: String,
        password: String
    ): Result<PrincipalUser> {
        delay(5000L)
        return Result.success(
            PrincipalUser(
                userId = "123456789",
                username = name,
                email = "$name@gmail.com",
                firstName = "FirstName",
                lastName = "LastName",
                phoneNumber = 9090909090L
            )
        )
    }

    override suspend fun logOut(): Result<*> {
        delay(5000L)
        return Result.success(true)
    }


}