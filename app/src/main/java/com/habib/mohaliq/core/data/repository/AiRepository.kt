package com.habib.mohaliq.core.data.repository

import com.habib.mohaliq.core.model.ChatTurn

interface AiRepository {

    /**
     * Sends the full conversation so far (including the newest user
     * message) and returns the assistant's final reply — after
     * transparently handling any tool-use round trips against the real
     * place catalog. [history] is display-level only (user/assistant
     * text); the system prompt and tool plumbing are internal details.
     */
    suspend fun sendMessage(history: List<ChatTurn>): Result<String>

}
