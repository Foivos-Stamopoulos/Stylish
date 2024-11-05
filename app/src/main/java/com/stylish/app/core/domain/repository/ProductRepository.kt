package com.stylish.app.core.domain.repository

import com.stylish.app.core.domain.model.Product
import com.stylish.app.core.domain.util.DataError
import com.stylish.app.core.domain.util.Result

interface ProductRepository {

    suspend fun getAllCategories(): Result<List<String>, DataError.Network>

    suspend fun getAllProducts(): Result<List<Product>, DataError>

    suspend fun getProductById(id: Int): Result<Product, DataError>

}