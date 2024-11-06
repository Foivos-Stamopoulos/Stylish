package com.stylish.app.core.presentation.util

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import com.google.android.material.snackbar.Snackbar
import com.stylish.app.R
import com.stylish.app.core.data.util.NetworkUtils

fun showSnackBar(message: String, rootView: View) {
    val snack = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
    snack.show()
}

fun Fragment.checkConnection(func: () ->  Unit, rootView: View) {
    if (NetworkUtils.isInternetAvailable(rootView.context)) {
        func()
    } else {
        showSnackBar(getString(R.string.error_no_internet), rootView)
    }
}

fun getFragmentAnimationNavOptions(): NavOptions {
    val navBuilder = NavOptions.Builder()
    navBuilder.setEnterAnim(R.anim.slide_in_left)
    navBuilder.setExitAnim(R.anim.slide_out_left)
    navBuilder.setPopEnterAnim(R.anim.slide_in_right)
    navBuilder.setPopExitAnim(R.anim.slide_out_right)
    return navBuilder.build()
}