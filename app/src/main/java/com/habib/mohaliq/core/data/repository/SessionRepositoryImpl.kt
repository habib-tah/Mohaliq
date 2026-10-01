package com.habib.mohaliq.core.data.repository

import com.habib.mohaliq.core.data.datastore.UserPreferencesDataStore
import com.habib.mohaliq.core.model.UserSession
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val dataStore: UserPreferencesDataStore
) : SessionRepository {

    override val session: Flow<UserSession> = dataStore.session

    override suspend fun login(name: String, email: String) {
        dataStore.login(name, email)
    }

    override suspend fun logout() {
        dataStore.logout()
    }

}
