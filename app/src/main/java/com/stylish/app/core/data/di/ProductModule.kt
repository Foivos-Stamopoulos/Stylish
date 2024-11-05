package com.stylish.app.core.data.di

import com.stylish.app.core.data.api.ProductApi
import com.stylish.app.core.data.mapper.ProductMapper
import com.stylish.app.core.data.repository.ProductRepositoryImpl
import com.stylish.app.core.data.service.ProductService
import com.stylish.app.core.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProductModule {

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        service: ProductService,
        productMapper: ProductMapper
    ): ProductRepository {
        return ProductRepositoryImpl(
            service,
            productMapper
        )
    }

}