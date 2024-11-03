package com.stylish.app.core.data.di

import com.stylish.app.core.data.mapper.UserLoginMapper
import com.stylish.app.core.data.repository.LoginRepositoryImpl
import com.stylish.app.core.data.service.LoginService
import com.stylish.app.core.domain.repository.DataStoreManager
import com.stylish.app.core.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class LoginModule {

    @Provides
    fun providesLoginRepository(
        userLoginMapper: UserLoginMapper,
        loginService: LoginService,
        dataStoreManager: DataStoreManager
    ): LoginRepository {
        return LoginRepositoryImpl(
            userLoginMapper, loginService, dataStoreManager
        )
    }

}