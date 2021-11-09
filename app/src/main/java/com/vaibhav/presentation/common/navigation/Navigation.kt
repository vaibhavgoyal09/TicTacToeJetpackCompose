package com.vaibhav.presentation.common.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.vaibhav.presentation.home_screen.HomeScreen
import com.vaibhav.presentation.offline_mode.username.EnterUserNameScreen
import com.vaibhav.presentation.online_mode.game.OnlineGameScreen
import com.vaibhav.presentation.online_mode.room.create_room.CreateNewRoomScreen
import com.vaibhav.presentation.online_mode.room.select_room.SelectRoomScreen
import com.vaibhav.presentation.online_mode.username.ChooseUserNameScreen

@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

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
            CreateNewRoomScreen(
                navController = navController,
                scaffoldState = scaffoldState
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

            val currentBackStackRoute = navBackStackEntry?.destination?.route
            val isOnlineGameScreenComposableVisible = currentBackStackRoute == it.destination.route

            OnlineGameScreen(
                isBackHandleEnabled = isOnlineGameScreenComposableVisible,
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}