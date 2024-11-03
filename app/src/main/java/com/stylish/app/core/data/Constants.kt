package com.stylish.app.core.data

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {

    const val DATA_STORE_NAME = "stylish_store"
    val PREFS_TOKEN = stringPreferencesKey("token")
    const val MAX_LENGTH_EMAIL = 50

}