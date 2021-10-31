package com.vaibhav.core.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Room(
    val name: String,
    val playersCount: Int = 1
)
