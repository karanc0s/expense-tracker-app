package com.karan.expensetracker.presentation.ui.screens.login

import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.karan.expensetracker.presentation.components.CustomTextField
import com.karan.expensetracker.presentation.navigation.Screen
import com.karan.expensetracker.R;

@Composable
fun LogInScreen(modifier: Modifier = Modifier , navController: NavController) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Placeholder for Logo
        Image(
            painter = painterResource(R.drawable.money_svg), // Replace with your logo resource
            contentDescription = "App Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 32.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Username or Email TextField
        CustomTextField(
            value = username,
            onValueChange = { username = it },
            label = "Username or Email"
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Password TextField
        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            keyboardType = KeyboardType.Password,
            isPassword = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = { navController.navigate(Screen.Permissions) },
            modifier = Modifier.fillMaxWidth(),
            enabled = username.isNotBlank() && password.isNotBlank()
        ) {
            Text("Submit")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Forgot Password and Sign Up Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = { /* Handle forgot password action */ }) {
                Text("Forgot Password?")
            }
            TextButton(
                onClick = {
                    navController.navigate(Screen.SignUp)
                }
            ) {
                Text("Sign Up")
            }
        }
    }

}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun LogInPreview() {
    LogInScreen(navController = rememberNavController())
}