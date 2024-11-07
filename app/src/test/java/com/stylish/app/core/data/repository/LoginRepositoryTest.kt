package com.stylish.app.core.data.repository

import com.stylish.app.core.data.Constants
import com.stylish.app.core.data.dto.UserLoginDto
import com.stylish.app.core.data.mapper.UserLoginMapper
import com.stylish.app.core.data.service.LoginService
import com.stylish.app.core.domain.model.UserLogin
import com.stylish.app.core.domain.repository.DataStoreManager
import com.stylish.app.core.domain.util.DataError
import com.stylish.app.core.domain.util.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginRepositoryTest {

    private val userLoginMapper = mockk<UserLoginMapper>()
    private val loginService = mockk<LoginService>()
    private val dataStoreManager = mockk<DataStoreManager>()
    private lateinit var loginRepository: LoginRepositoryImpl

    @Before
    fun setup() {
        loginRepository = LoginRepositoryImpl(userLoginMapper, loginService, dataStoreManager)
    }

    @Test
    fun `login should return Success when loginService returns Success`() = runTest {
        // Given
        val username = "test_user"
        val password = "test_pass"
        val token = "fakeToken"

        val userLoginDto = mockk<UserLoginDto>()
        val successResult = Result.Success(userLoginDto)
        val userLogin = UserLogin(token)
        coEvery { loginService.login(username, password) }.returns(successResult)
        every { userLoginMapper.mapFromEntity(userLoginDto) }.returns(userLogin)
        coEvery { dataStoreManager.storeValue(Constants.PREFS_TOKEN, userLogin.token) }.returns(Unit)

        // When
        val result = loginRepository.login(username, password)

        // Then
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { dataStoreManager.storeValue(Constants.PREFS_TOKEN, token) }
    }

    @Test
    fun `login should return Error and clear token when loginService returns Error`() = runTest {
        // Given
        val username = "test_user"
        val password = "test_pass"
        val networkError = DataError.Network.UNKNOWN
        val errorResult = Result.Error(networkError)

        coEvery { loginService.login(username, password) }.returns(errorResult)
        coEvery { dataStoreManager.removeValue(Constants.PREFS_TOKEN) }.returns(Unit)

        // When
        val result = loginRepository.login(username, password)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(networkError, (result as Result.Error).error)
        coVerify(exactly = 1) { (dataStoreManager).removeValue(Constants.PREFS_TOKEN) }
    }

    @Test
    fun `login should handle dataStoreManager storeValue failure gracefully when loginService returns Success`() = runTest {
        // Given
        val username = "test_user"
        val password = "test_pass"
        val token = "fakeToken"

        val userLoginDto = mockk<UserLoginDto>()
        val successResult = Result.Success(userLoginDto)
        val userLogin = UserLogin(token)

        coEvery { loginService.login(username, password) }.returns(successResult)
        every { userLoginMapper.mapFromEntity(userLoginDto) }.returns(userLogin)
        coEvery { dataStoreManager.storeValue(Constants.PREFS_TOKEN, userLogin.token) }.throws(RuntimeException("DataStore error"))

        // When
        val result = loginRepository.login(username, password)

        // Then
        assertTrue(result is Result.Error)
        coVerify(exactly = 1) { (dataStoreManager).storeValue(Constants.PREFS_TOKEN, token) }
    }

    @Test
    fun `login should handle dataStoreManager removeValue failure gracefully when loginService returns Error`() = runTest {
        // Given
        val username = "test_user"
        val password = "test_pass"
        val networkError = DataError.Network.UNKNOWN
        val errorResult = Result.Error(networkError)

        coEvery { loginService.login(username, password) }.returns(errorResult)
        coEvery { dataStoreManager.removeValue(Constants.PREFS_TOKEN) }.throws(RuntimeException("DataStore error"))

        // When
        val result = loginRepository.login(username, password)

        // Then
        assertTrue(result is Result.Error)
        coVerify(exactly = 1) { (dataStoreManager).removeValue(Constants.PREFS_TOKEN) }
    }

}