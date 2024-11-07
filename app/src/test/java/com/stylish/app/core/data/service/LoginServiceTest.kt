package com.stylish.app.core.data.service

import com.stylish.app.core.data.api.LoginApi
import com.stylish.app.core.data.dto.UserLoginDto
import com.stylish.app.core.data.dto.UserLoginPostDto
import com.stylish.app.core.data.util.NetworkHelper
import com.stylish.app.core.domain.util.DataError
import com.stylish.app.core.domain.util.Result
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginServiceTest {

    private val api = mockk<LoginApi>()
    private lateinit var loginService: LoginService

    @Before
    fun setup() {
        loginService = LoginService(api)
    }

    @Test
    fun `login should return Success when api login succeeds`() = runTest {
        // Given
        val username = "test_user"
        val password = "test_pass"
        val userLoginDto = UserLoginDto("fakeToken")

        coEvery { api.login(UserLoginPostDto(username, password)) }.returns(userLoginDto)

        // When
        val result = loginService.login(username, password)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(userLoginDto, (result as Result.Success).data)
    }

    @Test
    fun `login should return Network Error when api throws network exception`() = runTest {
        // Given
        val username = "test_user"
        val password = "test_pass"
        val networkError = DataError.Network.UNKNOWN
        val runtimeException = RuntimeException("Network failure")

        coEvery { api.login(UserLoginPostDto(username, password)) }.throws(runtimeException)
        mockkObject(NetworkHelper)
        every { NetworkHelper.exceptionToErrorResult(runtimeException) }.returns(Result.Error(networkError))

        // When
        val result = loginService.login(username, password)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(networkError, (result as Result.Error).error)

        unmockkObject(NetworkHelper)
    }

}