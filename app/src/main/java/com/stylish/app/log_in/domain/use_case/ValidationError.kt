package com.stylish.app.log_in.domain.use_case

import com.stylish.app.core.domain.util.Error

sealed interface ValidationError: Error {

    enum class EmailError: ValidationError {
        EMPTY,
        NOT_VALID_FORMAT,
        MORE_THAN_50_CHARACTERS
    }

    enum class PasswordError: ValidationError {
        TOO_SHORT,
        NO_UPPERCASE,
        NO_LOWERCASE,
        NO_DIGIT
    }

}