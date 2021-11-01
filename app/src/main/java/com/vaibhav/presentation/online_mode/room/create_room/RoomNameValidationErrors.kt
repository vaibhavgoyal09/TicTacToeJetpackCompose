package com.vaibhav.presentation.online_mode.room.create_room

import com.vaibhav.presentation.common.util.Error

sealed class RoomNameValidationErrors: Error() {

    object Empty: RoomNameValidationErrors()

    object Short: RoomNameValidationErrors()

    object Long: RoomNameValidationErrors()
}
