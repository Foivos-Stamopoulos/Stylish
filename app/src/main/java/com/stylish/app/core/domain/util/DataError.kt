package com.stylish.app.core.domain.util

sealed interface DataError: Error {

    enum class Network: DataError {
        UNAUTHORIZED,
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }

}