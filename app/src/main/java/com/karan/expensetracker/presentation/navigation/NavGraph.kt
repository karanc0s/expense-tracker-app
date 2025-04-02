package com.karan.expensetracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import androidx.navigation.compose.NavHost
import com.karan.expensetracker.presentation.ui.screens.home.HomeScreen
import com.karan.expensetracker.presentation.ui.screens.login.LogInScreen
import com.karan.expensetracker.presentation.ui.screens.PermissionScreen
import com.karan.expensetracker.presentation.ui.screens.signup.SignUpScreen
import com.karan.expensetracker.presentation.ui.screens.SplashScreen


@Composable
fun AppNavigation(
    navHostController: NavHostController,
) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.Home
    ){

        composable<Screen.Splash>{
            SplashScreen(navController = navHostController)
        }

        composable<Screen.LogIn>{
            LogInScreen(navController = navHostController)
        }

        composable<Screen.SignUp>{
            SignUpScreen()
        }

        composable<Screen.Permissions>{
            PermissionScreen(navController = navHostController)
        }

        composable<Screen.Home>{
            HomeScreen()
        }



    }
}