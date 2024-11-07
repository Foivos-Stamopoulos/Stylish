package com.stylish.app.log_in.presentation.util

import com.stylish.app.R
import com.stylish.app.core.presentation.util.UiText
import com.stylish.app.log_in.domain.use_case.ValidationError

fun ValidationError.UsernameError.asUiText(): UiText {
    return when (this) {
        ValidationError.UsernameError.EMPTY -> {
            UiText.StringResource(R.string.error_required_field)
        }
    }
}

fun ValidationError.PasswordError.asUiText(): UiText {
    return when (this) {
        ValidationError.PasswordError.EMPTY -> {
            UiText.StringResource(R.string.error_required_field)
        }
        ValidationError.PasswordError.TOO_SHORT -> {
            UiText.StringResource(R.string.error_password_too_short)
        }
    }
}