package com.vaibhav.core.models.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateRoomRequest(
    val roomName: String
)
