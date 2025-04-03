package com.karan.expensetracker.di

import com.karan.expensetracker.data.repository.TokenRepositoryIMPL
import com.karan.expensetracker.data.repository.UserRepositoryIMPL
import com.karan.expensetracker.domain.repository.TokenRepository
import com.karan.expensetracker.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DataDepModule {

    @Provides
    @Singleton
    fun providesUserRepository() : UserRepository {
        return UserRepositoryIMPL()
    }

    @Provides
    @Singleton
    fun providesTokenRepository() : TokenRepository {
        return TokenRepositoryIMPL()
    }
}