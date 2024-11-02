package com.stylish.app.core.data.repository

import com.stylish.app.core.data.Constants
import com.stylish.app.core.data.mapper.UserLoginMapper
import com.stylish.app.core.data.service.LoginService
import com.stylish.app.core.domain.repository.DataStoreManager
import com.stylish.app.core.domain.repository.LoginRepository
import com.stylish.app.core.domain.util.DataError
import com.stylish.app.core.domain.util.Result
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val userLoginMapper: UserLoginMapper,
    private val loginService: LoginService,
    private val dataStoreManager: DataStoreManager
) : LoginRepository {

    override suspend fun login(username: String, password: String): Result<Unit, DataError.Network> {
        return when (val result = loginService.login(username, password)) {
            is Result.Success -> {
                val userLogin = userLoginMapper.mapFromEntity(result.data)
                dataStoreManager.storeValue(Constants.PREFS_TOKEN, userLogin.token)
                Result.Success(Unit)
            }
            is Result.Error -> {
                dataStoreManager.removeValue(Constants.PREFS_TOKEN)
                Result.Error(result.error)
            }
        }
    }


}