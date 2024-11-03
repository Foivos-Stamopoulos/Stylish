package com.stylish.app.log_in.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stylish.app.log_in.domain.use_case.EmailPatternValidator
import com.stylish.app.log_in.domain.use_case.PasswordValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailPatternValidator: EmailPatternValidator,
    private val passwordValidator: PasswordValidator
) : ViewModel() {

    /*private val _emailFieldState = MutableLiveData<Resource<Int>>()
    val emailFieldState : LiveData<Resource<Int>> get() = _emailFieldState

    private val _passwordFieldState = MutableLiveData<Resource<Int>>()
    val passwordFieldState : LiveData<Resource<Int>> get() = _passwordFieldState

    private val _signIn = MutableLiveData<Event<Resource<UserReturn>>>()
    val signInLD: LiveData<Event<Resource<UserReturn>>> get() = _signIn

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> get() = _isLoading*/

}