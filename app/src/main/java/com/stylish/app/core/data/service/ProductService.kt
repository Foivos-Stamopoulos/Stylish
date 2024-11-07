package com.stylish.app.core.data.service

import com.stylish.app.core.data.api.ProductApi
import com.stylish.app.core.data.dto.ProductDto
import com.stylish.app.core.data.dto.ProductPatchDto
import com.stylish.app.core.data.util.NetworkHelper
import com.stylish.app.core.domain.util.DataError
import com.stylish.app.core.domain.util.Result
import javax.inject.Inject

class ProductService @Inject constructor(
    private val api: ProductApi
) {

    suspend fun fetchAllCategories(): Result<List<String>, DataError.Network> {
        return try {
            val categories = api.fetchAllCategories()
            Result.Success(categories)
        } catch (e: Exception) {
            NetworkHelper.exceptionToErrorResult(e)
        }
    }

    suspend fun fetchAllProducts(): Result<List<ProductDto>, DataError.Network> {
        return try {
            val products = api.fetchAllProducts()
            Result.Success(products)
        } catch (e: Exception) {
            NetworkHelper.exceptionToErrorResult(e)
        }
    }

    suspend fun fetchProductById(id: Int): Result<ProductDto, DataError.Network> {
        return try {
            val productDto = api.fetchProductById(id)
            Result.Success(productDto)
        } catch (e: Exception) {
            NetworkHelper.exceptionToErrorResult(e)
        }
    }

    suspend fun updateProduct(id: Int, productPatchDto: ProductPatchDto): Result<Unit, DataError.Network> {
        return try {
            api.updateProduct(id, productPatchDto)
            Result.Success(Unit)
        } catch (e: Exception) {
            NetworkHelper.exceptionToErrorResult(e)
        }
    }

}