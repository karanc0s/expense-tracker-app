package com.karan.expensetracker.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object Splash : Screen

    @Serializable
    data object LogIn : Screen

    @Serializable
    data object SignUp : Screen

    @Serializable
    data object Onboarding : Screen

    @Serializable
    data object Permissions : Screen

    @Serializable
    data object Home : Screen


}