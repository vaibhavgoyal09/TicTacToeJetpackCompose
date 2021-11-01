package com.vaibhav.presentation.online_mode.room.create_room

sealed class CreateRoomInputEvents {

    data class EnteredRoomName(val name: String): CreateRoomInputEvents()

    data class CreateRoom(val userName: String): CreateRoomInputEvents()
}
