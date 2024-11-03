package com.stylish.app.core.presentation.util

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackBar(message: String, rootView: View) {
    val snack = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
    snack.show()
}