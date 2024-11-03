package com.stylish.app.core.data.util

import com.stylish.app.core.domain.util.DataError
import com.stylish.app.core.domain.util.Result
import retrofit2.HttpException
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.cancellation.CancellationException

object NetworkHelper {

    fun exceptionToErrorResult(e: Exception): Result<Nothing, DataError.Network> {
        return when (e) {
            is UnresolvedAddressException -> {
                e.printStackTrace()
                Result.Error(DataError.Network.NO_INTERNET)
            }
            is IllegalArgumentException -> {
                e.printStackTrace()
                return Result.Error(DataError.Network.SERIALIZATION)
            }
            is HttpException -> {
                httpExceptionToErrorResult(e)
            }
            else -> {
                if (e is CancellationException) throw e
                e.printStackTrace()
                return Result.Error(DataError.Network.UNKNOWN)
            }
        }
    }

    private fun httpExceptionToErrorResult(
        e: HttpException,
    ): Result<Nothing, DataError.Network> {
        return when(e.code()) {
            401 -> Result.Error(DataError.Network.UNAUTHORIZED)
            408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
            429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
            in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
            else -> Result.Error(DataError.Network.UNKNOWN)
        }
    }

}