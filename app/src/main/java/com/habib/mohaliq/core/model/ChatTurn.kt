package com.habib.mohaliq.core.model

import java.util.UUID

data class ChatTurn(
    val id: String = UUID.randomUUID().toString(),
    val role: ChatRole,
    val content: String
)
