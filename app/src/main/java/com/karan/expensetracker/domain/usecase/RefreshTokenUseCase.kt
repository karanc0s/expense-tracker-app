package com.karan.expensetracker.domain.usecase

import com.karan.expensetracker.domain.repository.TokenRepository
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {

    suspend operator fun invoke(token : String) : Result<*> {
        return tokenRepository.refreshToken(token)
    }

}