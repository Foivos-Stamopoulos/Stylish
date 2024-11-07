package com.stylish.app.core.data.api

import com.stylish.app.core.data.dto.UserLoginPostDto
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: LoginApi

    @Before
    fun before() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }

    @Test
    fun `execute login request and parse response`() = runTest {
        // Given
        val username = "test_user"
        val password = "test_pass"
        val mockResponseJson = ClassLoader.getSystemResource("api_response/login.json").readText()
        val mockSuccessResponse = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setResponseCode(200)
            .setBody(mockResponseJson)
        server.enqueue(mockSuccessResponse)

        // When
        api.login(UserLoginPostDto(username, password))
        val request = server.takeRequest()

        // Then
        assertEquals("POST /auth/login HTTP/1.1", request.requestLine)
    }

}