package com.example.saitowapp.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import com.example.saitowapp.model.Data_Prefernce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataPrefernceRepository(context: Context) {
    private object PreferencesKeys {
        val EXPIRE_AT = intPreferencesKey("expireAt")
        val TOKEN = stringPreferencesKey("token")
        val ISLOGGEDIN = booleanPreferencesKey("isLoggedIn")
    }

    private val DATA_PREFERENCES_NAME: String = "data_preferences"
    private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences> =
        context.createDataStore(DATA_PREFERENCES_NAME)

    suspend fun setLoginData_Prefernce(expireAt: Int, token: String,isLoggedIn:Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.EXPIRE_AT] = expireAt
            preferences[PreferencesKeys.TOKEN] = token
            preferences[PreferencesKeys.ISLOGGEDIN] = isLoggedIn
        }
    }

    val getLoginData: Flow<Data_Prefernce> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            // Get our show completed value, defaulting to false if not set:
            val expireAt = preferences[PreferencesKeys.EXPIRE_AT] ?: Int
            val token = preferences[PreferencesKeys.TOKEN] ?: String
            val isLoggedIn = preferences[PreferencesKeys.ISLOGGEDIN] ?: Boolean
            Data_Prefernce(expireAt as Int, token as String,isLoggedIn as Boolean)
        }

    suspend fun clearDataStore(){
        dataStore.edit {
            it.clear()
        }
    }


}

