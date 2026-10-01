package com.habib.mohaliq.core.data.repository

import com.habib.mohaliq.core.model.UserSession
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    val session: Flow<UserSession>

    suspend fun login(name: String, email: String)

    suspend fun logout()

}
