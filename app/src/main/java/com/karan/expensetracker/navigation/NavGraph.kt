package com.karan.expensetracker.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import androidx.navigation.compose.NavHost
import com.karan.expensetracker.ui.screens.HomeScreen
import com.karan.expensetracker.ui.screens.PermissionScreen


@Composable
fun AppNavigation(
    navHostController: NavHostController,
) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.Permissions
    ){
        composable<Screen.Home>{
            HomeScreen()
        }

        composable<Screen.Permissions>{
            PermissionScreen(navController = navHostController)
        }

    }
}