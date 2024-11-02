package com.stylish.app.core.data.di

import android.content.Context
import com.stylish.app.core.data.repository.DataStoreManagerImpl
import com.stylish.app.core.domain.repository.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext appContext: Context): DataStoreManager =
        DataStoreManagerImpl(appContext)

}