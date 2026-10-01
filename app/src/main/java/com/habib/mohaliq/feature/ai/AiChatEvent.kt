package com.habib.mohaliq.feature.ai

sealed interface AiChatEvent {

    data object NavigateBack : AiChatEvent

}
