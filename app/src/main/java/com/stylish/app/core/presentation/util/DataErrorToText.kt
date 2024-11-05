package com.stylish.app.core.presentation.util

import com.stylish.app.R
import com.stylish.app.core.domain.util.DataError

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Network.NO_INTERNET -> {
            UiText.StringResource(R.string.error_no_internet)
        }
        DataError.Network.SERIALIZATION -> {
            UiText.StringResource(R.string.error_serialization)
        }
        DataError.Network.REQUEST_TIMEOUT -> {
            UiText.StringResource(R.string.error_request_timeout)
        }
        DataError.Network.TOO_MANY_REQUESTS -> {
            UiText.StringResource(R.string.error_too_many_requests)
        }
        DataError.Network.SERVER_ERROR -> {
            UiText.StringResource(R.string.error_server)
        }
        DataError.Network.UNAUTHORIZED -> {
            UiText.StringResource(R.string.error_unauthorized)
        }
        DataError.Network.UNKNOWN -> {
            UiText.StringResource(R.string.error_unknown)
        }
        DataError.Generic.UNKNOWN -> {
            UiText.StringResource(R.string.error_unknown)
        }
    }
}