package com.stylish.app.log_in.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stylish.app.core.data.util.Event
import com.stylish.app.core.domain.repository.LoginRepository
import com.stylish.app.core.domain.util.Result
import com.stylish.app.core.presentation.util.UiText
import com.stylish.app.core.presentation.util.asUiText
import com.stylish.app.log_in.domain.use_case.PasswordValidator
import com.stylish.app.log_in.domain.use_case.UsernameValidator
import com.stylish.app.log_in.presentation.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val passwordValidator: PasswordValidator,
    private val usernameValidator: UsernameValidator,
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _emailError = MutableLiveData<UiText?>()
    val emailError : LiveData<UiText?>
        get() = _emailError

    private val _passwordError = MutableLiveData<UiText?>()
    val passwordError : LiveData<UiText?>
        get() = _passwordError

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> get() = _isLoading

    private val _openHomeScreen = MutableLiveData<Event<Unit>>()
    val openHomeScreen: LiveData<Event<Unit>> get() = _openHomeScreen

    private val _snackBarMessage = MutableLiveData<Event<UiText>>()
    val snackBarMessage: LiveData<Event<UiText>> get() = _snackBarMessage

    fun onEmailChanged() {
        _emailError.value = null
    }

    fun onPasswordChanged() {
        _passwordError.value = null
    }

    fun validateCredentials(username: String, password: String) {
        val usernameResult = usernameValidator.execute(username)
        val passwordResult = passwordValidator.execute(password)

        if (usernameResult is Result.Success &&
            passwordResult is Result.Success) {
            submitData(username, password)
            return
        }
        if (usernameResult is Result.Error) {
            _emailError.value = usernameResult.error.asUiText()
        }
        if (passwordResult is Result.Error) {
            _passwordError.value = passwordResult.error.asUiText()
        }
    }

    private fun submitData(username: String, password: String) {
        _isLoading.value = Event(true)
        viewModelScope.launch {
            when (val result = loginRepository.login(username, password)) {
                is Result.Error -> {
                    _snackBarMessage.value = Event(result.error.asUiText())
                }
                is Result.Success -> {
                    _openHomeScreen.value = Event(Unit)
                }
            }
            _isLoading.value = Event(false)
        }
    }

}