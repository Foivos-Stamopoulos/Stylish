package com.stylish.app.log_in.presentation

import com.stylish.app.core.presentation.util.UiText

sealed class Effect {
    data object OpenHomeScreen: Effect()
    data class ShowMessage(val uiText: UiText): Effect()
}