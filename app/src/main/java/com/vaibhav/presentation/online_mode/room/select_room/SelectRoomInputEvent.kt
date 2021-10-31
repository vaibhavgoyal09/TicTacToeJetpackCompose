package com.vaibhav.presentation.online_mode.room.select_room

sealed class SelectRoomInputEvent {

    data class EnteredRoomName(val name: String) : SelectRoomInputEvent()

    data class JoinRoom(val userName: String, val roomName: String) : SelectRoomInputEvent()

    object Search : SelectRoomInputEvent()

    object Refresh : SelectRoomInputEvent()

    object CreateNewRoom : SelectRoomInputEvent()
}
