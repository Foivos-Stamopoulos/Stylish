package com.stylish.app.splash_screen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stylish.app.core.data.Constants
import com.stylish.app.core.domain.repository.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _userLoggedIn = MutableLiveData<Boolean>()
    val userLoggedIn: LiveData<Boolean> get() = _userLoggedIn

    init {
        viewModelScope.launch {
            delay(600) // On purpose delay so that the user has time to see the App Logo
            val token = dataStoreManager.readValue(Constants.PREFS_TOKEN).first()
            _userLoggedIn.value = isUserLoggedIn(token)
        }
    }

    private fun isUserLoggedIn(token: String?): Boolean {
        return !token.isNullOrEmpty()
    }

}