package com.vaibhav.presentation.common.navigation

sealed class Screen(val route: String) {

    object HomeScreen: Screen("home_screen_")

    object EnterUserNameScreen: Screen("enter_user_name_screen")

    object ChooseUserNameScreen: Screen("choose_user_name_screen")

    object SelectRoomScreen: Screen("select_room_screen")

    object OnlineGameScreen: Screen("online_game_screen")

    object CreateNewRoomScreen: Screen("create_new_room_screen")

    object OfflineGameScreen: Screen("offline_game_screen_route")
}