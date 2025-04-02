package com.karan.expensetracker.data.repository

import com.karan.expensetracker.domain.model.PrincipalUser
import com.karan.expensetracker.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryIMPL @Inject constructor() : UserRepository {

    override suspend fun logIn(
        name: String,
        password: String
    ): Result<PrincipalUser> {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(
        name: String,
        password: String
    ): Result<PrincipalUser> {
        TODO("Not yet implemented")
    }

    override suspend fun logOut(): Result<*> {
        TODO("Not yet implemented")
    }


}