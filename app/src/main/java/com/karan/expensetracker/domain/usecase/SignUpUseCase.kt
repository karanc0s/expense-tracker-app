package com.karan.expensetracker.domain.usecase

import com.karan.expensetracker.domain.model.PrincipalUser
import com.karan.expensetracker.domain.repository.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(username : String , password : String) : Result<PrincipalUser> {
        return userRepository.signUp(username , password)
    }


}