package com.vaibhav.core.util

import com.vaibhav.util.ResponseResult
import io.ktor.client.features.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okio.IOException

internal suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    methodCall: suspend () -> T
): ResponseResult<T> {
    return withContext(dispatcher) {
        try {
            ResponseResult.Success(methodCall.invoke())
        } catch (throwable: Throwable) {
            throwable.printStackTrace()

            when (throwable) {
                is ClientRequestException -> {
                    val httpStatusCode = throwable.response.status
                    if (httpStatusCode == HttpStatusCode.BadRequest) {
                        ResponseResult.Error(
                            throwable.response.status.description
                        )
                    } else {
                        ResponseResult.Error(
                            NetworkErrors.ERROR_UNKNOWN
                        )
                    }
                }
                is ServerResponseException -> {
                    ResponseResult.Error(NetworkErrors.ERROR_UNKNOWN)
                }
                is IOException -> {
                    ResponseResult.Error(NetworkErrors.INTERNET_ERROR)
                }
                else -> {
                    ResponseResult.Error(throwable.localizedMessage ?: NetworkErrors.ERROR_UNKNOWN)
                }
            }
        }
    }
}