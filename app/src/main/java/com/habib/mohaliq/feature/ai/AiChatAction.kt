package com.habib.mohaliq.feature.ai

sealed interface AiChatAction {

    data class InputChanged(val text: String) : AiChatAction

    data object SendClicked : AiChatAction

    data class SuggestionClicked(val text: String) : AiChatAction

    data object BackClicked : AiChatAction

}
