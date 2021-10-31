package com.vaibhav.presentation.online_mode.room.select_room

import com.vaibhav.presentation.common.util.Error

sealed class RoomEventErrors: Error() {

    object NoRoomsFound: RoomEventErrors()

    object NetworkError: RoomEventErrors()
}
