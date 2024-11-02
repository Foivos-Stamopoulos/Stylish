package com.stylish.app.core.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.stylish.app.core.data.Constants
import com.stylish.app.core.domain.repository.DataStoreManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
): DataStoreManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constants.DATA_STORE_NAME)

    override suspend fun <T> storeValue(key: Preferences.Key<T>, value: T?) {
        if (value == null) {
            removeValue(key)
        } else {
            context.dataStore.edit {
                it[key] = value
            }
        }
    }

    override suspend fun <T> readValue(key: Preferences.Key<T>): Flow<T?> {
        return context.dataStore.data
            .catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[key]
            }
    }

    override suspend fun <T> removeValue(key: Preferences.Key<T>) {
        context.dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

}