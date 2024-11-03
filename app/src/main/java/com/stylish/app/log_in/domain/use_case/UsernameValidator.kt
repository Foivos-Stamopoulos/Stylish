package com.stylish.app.log_in.domain.use_case

import com.stylish.app.core.domain.util.Result
import javax.inject.Inject

class UsernameValidator @Inject constructor() {

    fun execute(username: String): Result<Unit, ValidationError.UsernameError> {
        return if (username.isEmpty()) {
            Result.Error(ValidationError.UsernameError.EMPTY)
        } else {
            Result.Success(Unit)
        }
    }

}