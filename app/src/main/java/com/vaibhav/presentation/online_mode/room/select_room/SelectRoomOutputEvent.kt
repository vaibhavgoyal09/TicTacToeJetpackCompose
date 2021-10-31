package com.vaibhav.presentation.online_mode.room.select_room

sealed class SelectRoomOutputEvent {

    class Navigate(val route: String): SelectRoomOutputEvent()

    class ShowSnackBar(val message: String): SelectRoomOutputEvent()
}
