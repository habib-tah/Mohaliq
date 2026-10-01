package com.habib.mohaliq.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habib.mohaliq.core.data.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<LoginEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {

            is LoginAction.EmailChanged -> {
                _uiState.update { it.copy(email = action.value, emailError = null) }
            }

            is LoginAction.PasswordChanged -> {
                _uiState.update { it.copy(password = action.value, passwordError = null) }
            }

            LoginAction.LoginClicked -> {

                val state = _uiState.value

                val emailError =
                    if (!state.email.contains("@")) "Enter a valid email" else null

                val passwordError =
                    if (state.password.length < 6) {
                        "Password must be at least 6 characters"
                    } else {
                        null
                    }

                if (emailError != null || passwordError != null) {
                    _uiState.update {
                        it.copy(
                            emailError = emailError,
                            passwordError = passwordError
                        )
                    }
                    return
                }

                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true) }

                    try {
                        delay(1000.milliseconds)

                        sessionRepository.login(
                            name = state.email.substringBefore("@"),
                            email = state.email
                        )

                        _events.send(LoginEvent.NavigateHome)

                    } finally {
                        _uiState.update { it.copy(isLoading = false) }
                    }
                }
            }

            LoginAction.GoogleSignInClicked -> {
                viewModelScope.launch {
                    _events.send(
                        LoginEvent.ShowError("Google Sign-In isn't wired up")
                    )
                }
            }

            LoginAction.RegisterLinkClicked -> {
                viewModelScope.launch {
                    _events.send(LoginEvent.NavigateToRegister)
                }
            }
        }
    }
}