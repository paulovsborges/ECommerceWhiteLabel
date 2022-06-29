package com.pvsb.core.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

val Context.dataStoreImpl get() = DataStorePreferences(this)

suspend fun <T> Context.getValueFromDataStore(
    keyName: Preferences.Key<T>,
    defaultValue: T,
    value: (T) -> Unit
) {

    dataStoreImpl.getValue(keyName, defaultValue).collectLatest {
        value.invoke(it)
    }
}

suspend fun <T> Context.putValueOnDataStore(keyName: Preferences.Key<T>, value: T) {
    dataStoreImpl.putValue(keyName, value)
}

suspend fun <T> Context.removeValueFromDataStore(keyName: Preferences.Key<T>) {
    dataStoreImpl.removeValue(keyName)
}

suspend fun Context.clearDataStore() {
    dataStoreImpl.clear()
}

class DataStorePreferences(private val context: Context) {

    private companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "e_commerce")
    }

    fun <T> getValue(keyName: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return context.dataStore.data
            .catch {
                throw it
            }
            .map {
                val result = it[keyName] ?: defaultValue
                result
            }
    }

    suspend fun <T> putValue(keyName: Preferences.Key<T>, value: T) {
        context.dataStore.edit {
            it[keyName] = value
        }
    }

    suspend fun <T> removeValue(keyName: Preferences.Key<T>) {
        context.dataStore.edit {
            it.remove(keyName)
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}


