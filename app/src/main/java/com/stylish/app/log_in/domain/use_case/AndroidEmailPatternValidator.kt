package com.stylish.app.log_in.domain.use_case

import com.stylish.app.core.data.Constants
import com.stylish.app.core.domain.util.Result
import javax.inject.Inject

class AndroidEmailPatternValidator @Inject constructor(): EmailPatternValidator {

    override fun isValidEmail(email: String): Result<Unit, ValidationError.EmailError> {

        if (email.isEmpty()) {
            return Result.Error(ValidationError.EmailError.EMPTY)
        }
        if (email.length > Constants.MAX_LENGTH_EMAIL) {
            return Result.Error(ValidationError.EmailError.MORE_THAN_50_CHARACTERS)
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.Error(ValidationError.EmailError.NOT_VALID_FORMAT)
        }

        return Result.Success(Unit)
    }

}

