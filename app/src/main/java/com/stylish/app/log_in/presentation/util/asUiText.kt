package com.stylish.app.log_in.presentation.util

import com.stylish.app.R
import com.stylish.app.core.presentation.util.UiText
import com.stylish.app.log_in.domain.use_case.ValidationError

fun ValidationError.EmailError.asUiText(): UiText {
    return when (this) {
        ValidationError.EmailError.EMPTY -> {
            UiText.StringResource(R.string.error_required_field)
        }
        ValidationError.EmailError.NOT_VALID_FORMAT -> {
            UiText.StringResource(R.string.error_invalid_email)
        }
        ValidationError.EmailError.MORE_THAN_50_CHARACTERS -> {
            UiText.StringResource(R.string.error_email_too_big)
        }
    }
}

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