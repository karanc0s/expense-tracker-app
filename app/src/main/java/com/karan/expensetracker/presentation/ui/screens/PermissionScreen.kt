package com.karan.expensetracker.presentation.ui.screens


import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.karan.expensetracker.presentation.navigation.Screen


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionScreen(navController: NavController) {

    val permission = rememberPermissionState(
        Manifest.permission.READ_SMS
    )
    val per = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS
        )
    )



    Log.e("TAG", "PermissionScreen: in the method", )

    if(per.allPermissionsGranted){
        LaunchedEffect(Unit) {
            Log.e("TAG", "PermissionScreen: Going to Home ", )
            navController.popBackStack()
            navController.navigate(Screen.Home)
        }

    } else{
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val textToShow = if (per.shouldShowRationale) {
                "Permission is important for this app. Please grant the permission."
            } else {
                "Permission required for this application to work. " +
                        "Please grant the permission"
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 20.dp),
                text = textToShow,
                textAlign = TextAlign.Center
            )
            Button(onClick = { per.launchMultiplePermissionRequest() }) {
                Text("Request permission")
            }
        }
    }



}