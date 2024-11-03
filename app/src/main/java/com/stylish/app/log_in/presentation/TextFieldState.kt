package com.stylish.app.log_in.presentation

import com.stylish.app.core.presentation.util.UiText

data class TextFieldState(
    val text: String = "",
    val hint: UiText = UiText.StringDynamic(""),
    val isHintVisible: Boolean = false,
    val error: UiText? = null
)