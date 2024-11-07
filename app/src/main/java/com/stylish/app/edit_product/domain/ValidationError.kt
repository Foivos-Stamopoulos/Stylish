package com.stylish.app.edit_product.domain

import com.stylish.app.core.domain.util.Error

sealed interface ValidationError: Error {

    enum class InputTextError: ValidationError {
        EMPTY
    }

    enum class PriceError: ValidationError {
        INVALID,
        EMPTY
    }

}