package com.habib.mohaliq.feature.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habib.mohaliq.core.data.repository.AiRepository
import com.habib.mohaliq.core.model.ChatRole
import com.habib.mohaliq.core.model.ChatTurn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AiChatViewModel @Inject constructor(
    private val aiRepository: AiRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AiChatUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<AiChatEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onAction(action: AiChatAction) {
        when (action) {

            is AiChatAction.InputChanged -> {
                _uiState.update { it.copy(inputText = action.text) }
            }

            is AiChatAction.SuggestionClicked -> {
                _uiState.update { it.copy(inputText = action.text) }
            }

            AiChatAction.SendClicked -> {
                sendCurrentInput()
            }

            AiChatAction.BackClicked -> {
                viewModelScope.launch {
                    _events.send(AiChatEvent.NavigateBack)
                }
            }

        }
    }

    private fun sendCurrentInput() {

        val state = _uiState.value
        if (!state.canSend) return

        val userTurn = ChatTurn(role = ChatRole.USER, content = state.inputText.trim())
        val historyWithUser = state.messages + userTurn

        _uiState.update {
            it.copy(
                messages = historyWithUser,
                inputText = "",
                isSending = true
            )
        }

        viewModelScope.launch {

            try {

                val result = aiRepository.sendMessage(historyWithUser)

                val replyTurn = result.fold(
                    onSuccess = { text ->
                        ChatTurn(
                            role = ChatRole.ASSISTANT,
                            content = text.ifBlank {
                                "I didn't quite get that — could you rephrase?"
                            }
                        )
                    },
                    onFailure = {
                        ChatTurn(
                            role = ChatRole.ASSISTANT,
                            content = "Sorry, something went wrong reaching the assistant. Please try again."
                        )
                    }
                )

                _uiState.update {
                    it.copy(
                        messages = it.messages + replyTurn,
                        isSending = false
                    )
                }

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        messages = it.messages + ChatTurn(
                            role = ChatRole.ASSISTANT,
                            content = "Sorry, something went wrong reaching the assistant. Please try again."
                        ),
                        isSending = false
                    )
                }

            }

        }

    }

}