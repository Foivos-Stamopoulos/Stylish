package com.stylish.app.log_in.domain.use_case

import com.stylish.app.core.data.Constants
import javax.inject.Inject

class PasswordValidator @Inject constructor() {

    fun execute(password: String): PasswordValidationResult<Unit, ValidationError.PasswordError> {
        val hasValidLength = hasValidLength(password)
        val hasUppercase = hasUppercase(password)
        val hasLowercase = hasLowercase(password)
        val hasDigit = hasDigit(password)

        val errors = mutableListOf<ValidationError.PasswordError>()
        if (!hasValidLength) {
            errors.add(ValidationError.PasswordError.TOO_SHORT)
        }
        if (!hasUppercase) {
            errors.add(ValidationError.PasswordError.NO_UPPERCASE)
        }
        if (!hasLowercase) {
            errors.add(ValidationError.PasswordError.NO_LOWERCASE)
        }
        if (!hasDigit) {
            errors.add(ValidationError.PasswordError.NO_DIGIT)
        }

        if (errors.isEmpty()) {
            return PasswordValidationResult.Success(Unit)
        }
        return PasswordValidationResult.Error(errors)
    }

    private fun hasValidLength(password: String): Boolean {
        return password.length >= Constants.MIN_LENGTH_PASSWORD
    }

    private fun hasUppercase(password: String): Boolean {
        return password.any { it.isUpperCase() }
    }

    private fun hasLowercase(password: String): Boolean {
        return password.any { it.isLowerCase() }
    }

    private fun hasDigit(password: String): Boolean {
        return password.any { it.isDigit() }
    }

    sealed interface PasswordValidationResult<out D, out E: ValidationError> {
        data class Success<out D, out E: ValidationError>(val data: D): PasswordValidationResult<D, E>
        data class Error<out D, out E: ValidationError>(val errors: List<E>): PasswordValidationResult<D, E>
    }

}