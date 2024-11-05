package com.stylish.app.core.data.repository

import com.stylish.app.core.data.mapper.ProductMapper
import com.stylish.app.core.data.service.ProductService
import com.stylish.app.core.domain.model.Product
import com.stylish.app.core.domain.repository.ProductRepository
import com.stylish.app.core.domain.util.DataError
import com.stylish.app.core.domain.util.Result
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val service: ProductService,
    private val productMapper: ProductMapper
) : ProductRepository {

    override suspend fun getAllCategories(): Result<List<String>, DataError.Network> {
        return service.fetchAllCategories()
    }

    override suspend fun getAllProducts(): Result<List<Product>, DataError> {
        return when (val result = service.fetchAllProducts()) {
            is Result.Success -> {
                try {
                    val products = result.data.map { productMapper.mapFromEntity(it) }
                    Result.Success(products)
                } catch (e: Exception) {
                    Result.Error(DataError.Generic.UNKNOWN)
                }
            }
            is Result.Error -> {
                Result.Error(result.error)
            }
        }
    }

    override suspend fun getProductById(id: Int): Result<Product, DataError> {
        return when (val result = service.fetchProductById(id)) {
            is Result.Success -> {
                try {
                    Result.Success(productMapper.mapFromEntity(result.data))
                } catch (e: Exception) {
                    Result.Error(DataError.Generic.UNKNOWN)
                }
            }
            is Result.Error -> {
                Result.Error(result.error)
            }
        }
    }

}