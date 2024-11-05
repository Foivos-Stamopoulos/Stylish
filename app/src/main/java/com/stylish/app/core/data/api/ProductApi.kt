package com.stylish.app.core.data.api

import com.stylish.app.core.data.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET("products/categories")
    suspend fun fetchAllCategories(): List<String>

    @GET("products")
    suspend fun fetchAllProducts(): List<ProductDto>

    @GET("products/{id}")
    suspend fun fetchProductById(
        @Path("id") id: Int
    ): ProductDto

}