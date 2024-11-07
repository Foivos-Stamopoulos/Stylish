package com.stylish.app.edit_product.domain

import com.stylish.app.core.domain.util.Result
import javax.inject.Inject

class InputTextValidator @Inject constructor() {

    operator fun invoke(text: String?): Result<Unit, ValidationError.InputTextError> {

        return if (text.isNullOrEmpty()) {
            Result.Error(ValidationError.InputTextError.EMPTY)
        } else {
            Result.Success(Unit)
        }

    }

}