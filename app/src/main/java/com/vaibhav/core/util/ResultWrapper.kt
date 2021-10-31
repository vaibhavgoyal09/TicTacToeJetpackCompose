package com.vaibhav.core.util

import com.vaibhav.util.ResponseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

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
                is HttpException -> {
                    ResponseResult.Error(
                        throwable.response()?.errorBody()?.toString() ?: NetworkErrors.ERROR_UNKNOWN
                    )
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