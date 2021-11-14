package com.vaibhav.core.models.response

data class JoinRoomResponse(
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val existingPlayerUserName: String? = null
)