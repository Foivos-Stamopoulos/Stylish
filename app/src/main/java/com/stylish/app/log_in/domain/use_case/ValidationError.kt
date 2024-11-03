package com.stylish.app.log_in.domain.use_case

import com.stylish.app.core.domain.util.Error

sealed interface ValidationError: Error {

    enum class UsernameError: ValidationError {
        EMPTY
    }

    enum class EmailError: ValidationError {
        EMPTY,
        NOT_VALID_FORMAT,
        MORE_THAN_50_CHARACTERS
    }

    enum class PasswordError: ValidationError {
        EMPTY,
        TOO_SHORT
    }

}