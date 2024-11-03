package com.stylish.app.log_in.domain.use_case

import com.stylish.app.core.domain.util.Result

interface EmailPatternValidator {

    fun isValidEmail(email: String): Result<Unit, ValidationError.EmailError>

}