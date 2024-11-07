package com.stylish.app.core.data.api

import com.stylish.app.core.data.dto.ProductDto
import com.stylish.app.core.data.dto.ProductPatchDto
import com.stylish.app.core.data.dto.ProductPatchResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
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

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body productPatchDto: ProductPatchDto
    ): ProductPatchResponseDto

}