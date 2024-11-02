package com.stylish.app.core.domain.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreManager {

    suspend fun <T> storeValue(key: Preferences.Key<T>, value: T?)

    suspend fun <T> readValue(key: Preferences.Key<T>): Flow<T?>

    suspend fun <T> removeValue(key: Preferences.Key<T>)

}