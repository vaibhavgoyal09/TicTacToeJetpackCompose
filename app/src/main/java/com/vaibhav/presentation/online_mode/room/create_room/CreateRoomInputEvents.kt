package com.vaibhav.presentation.online_mode.room.create_room

sealed class CreateRoomInputEvents {

    data class EnteredRoomName(val name: String): CreateRoomInputEvents()

    object CreateRoom: CreateRoomInputEvents()
}
