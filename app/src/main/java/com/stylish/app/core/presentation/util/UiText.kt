package com.stylish.app.core.presentation.util

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {

    data class StringDynamic(val value: String): UiText()

    class StringResource(
        @StringRes val resourceId: Int,
        vararg val args: Any
    ): UiText()

    fun asString(context: Context): String {
        return when (this) {
            is StringDynamic -> value
            is StringResource -> context.getString(resourceId, *args)
        }
    }

}