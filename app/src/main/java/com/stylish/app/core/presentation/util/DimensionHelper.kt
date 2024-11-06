package com.stylish.app.core.presentation.util

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

object DimensionHelper {

    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    fun getFullScreenWidth(): Int = Resources.getSystem().displayMetrics.widthPixels

}