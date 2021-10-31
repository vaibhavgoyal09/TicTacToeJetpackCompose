package com.vaibhav.util

sealed class ResponseResult<T>(val data: T? = null, val error: String? = null) {

    class Success<T>(data: T): ResponseResult<T>(data = data)

    class Error<T>(error: String): ResponseResult<T>(error = error)
}
