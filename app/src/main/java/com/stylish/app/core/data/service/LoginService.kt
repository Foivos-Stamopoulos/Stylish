package com.stylish.app.core.data.service

import com.stylish.app.core.data.api.LoginApi
import com.stylish.app.core.data.dto.UserLoginDto
import com.stylish.app.core.data.dto.UserLoginPostDto
import com.stylish.app.core.data.util.NetworkHelper
import com.stylish.app.core.domain.util.DataError
import com.stylish.app.core.domain.util.Result
import javax.inject.Inject

class LoginService @Inject constructor(
    private val api: LoginApi
) {

    suspend fun login(username: String, password: String): Result<UserLoginDto, DataError.Network> {
        return try {
            Result.Success(api.login(UserLoginPostDto(username, password)))
        } catch (e: Exception) {
            NetworkHelper.exceptionToErrorResult(e)
        }
    }

}