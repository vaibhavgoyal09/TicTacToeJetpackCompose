package com.vaibhav.presentation.navigation

sealed class Screen(val route: String) {

    object HomeScreen: Screen("home_screen_")

    object EnterUserNameScreen: Screen("enter_user_name_screen")

    object ChooseUserNameScreen: Screen("choose_user_name_screen")

    object SelectRoomScreen: Screen("select_room_screen")
}