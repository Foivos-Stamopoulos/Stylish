package com.stylish.app.core.domain.repository

import com.stylish.app.core.domain.util.DataError
import com.stylish.app.core.domain.util.Result

interface LoginRepository {

    suspend fun login(username: String, password: String): Result<Unit, DataError>

}