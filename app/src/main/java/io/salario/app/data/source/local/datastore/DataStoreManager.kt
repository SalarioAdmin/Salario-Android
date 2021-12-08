package io.salario.app.data.source.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

class DataStoreManager(private val context: Context) {

    suspend fun saveAccessToken(accessToken: String) {
        context.dataStore.edit {
            it[ACCESS_TOKEN_KEY] = accessToken
        }
    }

    suspend fun getAccessToken() = context.dataStore.data.map {
        it[ACCESS_TOKEN_KEY] ?: ""
    }

    suspend fun saveRefreshToken(accessToken: String) {
        context.dataStore.edit {
            it[REFRESH_TOKEN_KEY] = accessToken
        }
    }

    suspend fun getRefreshToken() = context.dataStore.data.map {
        it[REFRESH_TOKEN_KEY] ?: ""
    }

    companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("REFRESH_TOKEN")
    }

}