package com.vaibhav.core.models.ws

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
abstract class BaseModel(val type: String)
