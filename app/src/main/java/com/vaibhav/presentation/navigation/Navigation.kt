package com.vaibhav.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vaibhav.presentation.username.choose_username.ChooseUserNameScreen
import com.vaibhav.presentation.username.enter_username.EnterUserNameScreen
import com.vaibhav.presentation.home_screen.HomeScreen
import com.vaibhav.presentation.select_room.SelectRoomScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {

        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.EnterUserNameScreen.route) {
            EnterUserNameScreen(navController = navController)
        }

        composable(
            route = Screen.SelectRoomScreen.route + "/{userName}",
            arguments = listOf(
                navArgument("userName") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {

            val userName = it.arguments?.getString("userName").toString()

            SelectRoomScreen(
                navController = navController,
                userName = userName
            )
        }

        composable(Screen.ChooseUserNameScreen.route) {
            ChooseUserNameScreen(navController = navController)
        }
    }
}