package com.stylish.app.edit_product.presentation.util

import com.stylish.app.R
import com.stylish.app.core.presentation.util.UiText
import com.stylish.app.edit_product.domain.ValidationError

fun ValidationError.InputTextError.asUiText(): UiText {
    return when (this) {
        ValidationError.InputTextError.EMPTY -> {
            UiText.StringResource(R.string.error_required_field)
        }
    }
}

fun ValidationError.PriceError.asUiText(): UiText {
    return when (this) {
        ValidationError.PriceError.INVALID -> {
            UiText.StringResource(R.string.error_invalid_price)
        }
        ValidationError.PriceError.EMPTY -> {
            UiText.StringResource(R.string.error_required_field)
        }
    }
}