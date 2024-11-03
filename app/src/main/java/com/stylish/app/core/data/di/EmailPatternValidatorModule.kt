package com.stylish.app.core.data.di

import com.stylish.app.log_in.domain.use_case.AndroidEmailPatternValidator
import com.stylish.app.log_in.domain.use_case.EmailPatternValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class EmailPatternValidatorModule {

    @Provides
    @ViewModelScoped
    fun provideEmailPatternValidator(): EmailPatternValidator {
        return AndroidEmailPatternValidator()
    }

}