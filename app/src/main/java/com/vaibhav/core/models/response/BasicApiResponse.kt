package com.vaibhav.core.models.response

data class BasicApiResponse(
    val isSuccessful: Boolean = true,
    val message: String? = null
)
