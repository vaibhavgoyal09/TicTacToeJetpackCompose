package com.vaibhav.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vaibhav.presentation.online_mode.username.ChooseUserNameScreen
import com.vaibhav.presentation.offline_mode.username.EnterUserNameScreen
import com.vaibhav.presentation.home_screen.HomeScreen
import com.vaibhav.presentation.online_mode.game.OnlineGameScreen
import com.vaibhav.presentation.online_mode.room.create_room.CreateNewRoomScreen
import com.vaibhav.presentation.online_mode.room.select_room.SelectRoomScreen

@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState
) {
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
                userName = userName,
                scaffoldState = scaffoldState
            )
        }

        composable(Screen.ChooseUserNameScreen.route) {
            ChooseUserNameScreen(navController = navController)
        }

        composable(
            route = Screen.CreateNewRoomScreen.route + "/{userName}",
            arguments = listOf(
                navArgument(
                    name = "userName"
                ) {
                    nullable = false
                    type = NavType.StringType
                }
            )
        ) {
            val userName = it.arguments?.getString("userName").toString()
            CreateNewRoomScreen(
                navController = navController,
                scaffoldState = scaffoldState,
                userName = userName
            )
        }

        composable(
            route = Screen.OnlineGameScreen.route + "/{roomName}/{userName}",
            arguments = listOf(
                navArgument("roomName") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument(
                    "userName"
                ) {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            val roomName = it.arguments?.getString("roomName").toString()
            val userName = it.arguments?.getString("userName").toString()
            OnlineGameScreen(roomName = roomName,userName = userName, onNavigateUp = {
                navController.popBackStack()
            })
        }
    }
}