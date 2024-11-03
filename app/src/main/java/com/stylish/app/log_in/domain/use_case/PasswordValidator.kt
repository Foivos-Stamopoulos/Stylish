package com.stylish.app.log_in.domain.use_case

import com.stylish.app.core.domain.util.Result
import javax.inject.Inject

class PasswordValidator @Inject constructor() {

    fun execute(password: String): Result<Unit, ValidationError.PasswordError> {
        return if (password.isEmpty()) {
            Result.Error(ValidationError.PasswordError.EMPTY)
        } else {
            Result.Success(Unit)
        }
    }

}