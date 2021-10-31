package com.vaibhav.presentation.online_mode.room.select_room

import com.vaibhav.core.models.Room

data class RoomsEventState(
    val rooms: List<Room>? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)
