package com.habib.mohaliq.feature.ai

import com.habib.mohaliq.core.model.ChatTurn

data class AiChatUiState(
    val messages: List<ChatTurn> = emptyList(),
    val inputText: String = "",
    val isSending: Boolean = false
) {
    val canSend: Boolean
        get() = inputText.isNotBlank() && !isSending
}
