//package com.example.infoday
//
//import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.core.booleanPreferencesKey
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.preferencesDataStore
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//
//class UserPreferences(private val context: Context) {
//
//    // to make sure there's only one instance
//    companion object {
//        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
//        val DARK_MODE = booleanPreferencesKey("dark_mode")
//    }
//
//    // get the mode
//    val getMode: Flow<Boolean?> = context.dataStore.data
//        .map { preferences ->
//            preferences[DARK_MODE] ?: false
//        }
//
//    // save mode into datastore
//    suspend fun saveMode(mode: Boolean) {
//        context.dataStore.edit { preferences ->
//            preferences[DARK_MODE] = mode
//        }
//    }
//}

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor (private val dataStore: DataStore<Preferences>) {

    companion object {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        private var instance: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return instance ?: synchronized(this) {
                instance ?: UserPreferences(dataStore).also { instance = it }
            }
        }
    }

    // get the mode
    val getMode: Flow<Boolean?> = dataStore.data
        .map { preferences ->
            preferences[DARK_MODE] ?: false
        }

    // save mode into datastore
    suspend fun saveMode(mode: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE] = mode
        }
    }
}