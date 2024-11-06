package com.stylish.app.core.presentation.util

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.stylish.app.R
import com.stylish.app.core.data.util.NetworkUtils

fun Fragment.showSnackBar(message: String, rootView: View) {
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