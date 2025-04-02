package com.karan.expensetracker.domain.usecase

import com.karan.expensetracker.domain.model.PrincipalUser
import com.karan.expensetracker.domain.repository.UserRepository
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(username : String , password : String) : Result<PrincipalUser> {
        if (username.isBlank() || password.isBlank()) {
            return Result.failure(Exception("Invalid Credentials"))
        }
        return userRepository.logIn(username , password)
    }
}