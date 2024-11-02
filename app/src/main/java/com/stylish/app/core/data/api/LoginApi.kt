package com.stylish.app.core.data.api

import com.stylish.app.core.data.dto.UserLoginDto
import com.stylish.app.core.data.dto.UserLoginPostDto
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("auth/login")
    suspend fun login(
        @Body userLoginPostDto: UserLoginPostDto
    ): UserLoginDto

}