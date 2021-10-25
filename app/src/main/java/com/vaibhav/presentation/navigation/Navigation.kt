package com.vaibhav.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vaibhav.presentation.enter_username.EnterUserNameScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.EnterUserNameScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.EnterUserNameScreen.route) {
            EnterUserNameScreen(navController = navController)
        }
    }
}