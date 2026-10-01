package com.habib.mohaliq.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.habib.mohaliq.core.model.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private object Keys {
    val IS_LOGGED_IN = stringPreferencesKey("is_logged_in")
    val NAME = stringPreferencesKey("user_name")
    val EMAIL = stringPreferencesKey("user_email")
}

/**
 * Stores the local demo authentication session using Preferences DataStore.
 *
 * Mohaliq intentionally uses local authentication so the app can be
 * downloaded and tested without requiring a backend or external auth service.
 */
class UserPreferencesDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val session: Flow<UserSession> = dataStore.data.map { prefs ->
        UserSession(
            isLoggedIn = prefs[Keys.IS_LOGGED_IN] == "true",
            name = prefs[Keys.NAME] ?: "",
            email = prefs[Keys.EMAIL] ?: ""
        )
    }

    suspend fun login(name: String, email: String) {
        dataStore.edit { prefs ->
            prefs[Keys.IS_LOGGED_IN] = "true"
            prefs[Keys.NAME] = name
            prefs[Keys.EMAIL] = email
        }
    }

    suspend fun logout() {
        dataStore.edit { prefs ->
            prefs[Keys.IS_LOGGED_IN] = "false"
        }
    }

}
