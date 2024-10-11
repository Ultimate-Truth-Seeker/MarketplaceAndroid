package com.example.marketplace.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marketplace.ui.view.LoginScreen

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "/",
        modifier = modifier) {
        composable(route = "/") {
            LoginScreen(
                navController = navController,
                onLoginClick = {},
                onRegisterClick = {}
            )
        }
    }
}