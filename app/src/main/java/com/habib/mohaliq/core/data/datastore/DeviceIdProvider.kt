package com.habib.mohaliq.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject

private val DEVICE_ID = stringPreferencesKey("device_id")

/**
 * A random, anonymous ID generated once per app install and reused after
 * that — not tied to any personal info or hardware identifier. Only used
 * so the AI proxy (Cloudflare Worker) can rate-limit per installation
 * instead of globally. Reinstalling the app produces a new one.
 */
class DeviceIdProvider @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun getOrCreate(): String {

        val existing = dataStore.data.first()[DEVICE_ID]

        if (existing != null) return existing

        val newId = UUID.randomUUID().toString()

        dataStore.edit { prefs ->
            prefs[DEVICE_ID] = newId
        }

        return newId

    }

}
