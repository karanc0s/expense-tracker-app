package com.karan.expensetracker.domain.repository

import com.karan.expensetracker.domain.model.PrincipalUser

interface UserRepository {

    suspend fun logIn(name : String , password : String) : Result<PrincipalUser>

    suspend fun signUp(name : String , password : String) : Result<PrincipalUser>

    suspend fun logOut() : Result<*>

}