package com.vaibhav.core.models.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasicApiResponse(
    val isSuccessful: Boolean = true,
    val message: String? = null
)