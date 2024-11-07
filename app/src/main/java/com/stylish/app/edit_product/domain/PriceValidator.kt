package com.stylish.app.edit_product.domain

import com.stylish.app.core.domain.util.Result
import javax.inject.Inject

class PriceValidator @Inject constructor() {

    operator fun invoke(price: String?): Result<Float, ValidationError.PriceError> {
        return if (price.isNullOrEmpty()) {
            Result.Error(ValidationError.PriceError.EMPTY)
        } else {
            try {
                val priceFloat = price.toFloat()
                Result.Success(priceFloat)
            } catch (e: Exception) {
                Result.Error(ValidationError.PriceError.INVALID)
            }
        }
    }

}