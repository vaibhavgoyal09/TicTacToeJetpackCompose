package com.vaibhav.core.models.ws

import com.squareup.moshi.JsonClass
import com.vaibhav.util.Constants.TYPE_JOIN_ROOM

@JsonClass(generateAdapter = true)
data class JoinRoom(
    val userName: String,
    val roomName: String,
    val clientId: String
): BaseModel(TYPE_JOIN_ROOM)
