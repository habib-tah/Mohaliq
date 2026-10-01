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
class RegisterViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<RegisterEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onAction(action: RegisterAction) {
        when (action) {

            is RegisterAction.NameChanged -> {
                _uiState.update { it.copy(name = action.value, nameError = null) }
            }

            is RegisterAction.EmailChanged -> {
                _uiState.update { it.copy(email = action.value, emailError = null) }
            }

            is RegisterAction.PasswordChanged -> {
                _uiState.update { it.copy(password = action.value, passwordError = null) }
            }

            is RegisterAction.ConfirmPasswordChanged -> {
                _uiState.update {
                    it.copy(
                        confirmPassword = action.value,
                        confirmPasswordError = null
                    )
                }
            }

            RegisterAction.RegisterClicked -> {

                val state = _uiState.value

                val nameError =
                    if (state.name.isBlank()) "Enter your name" else null

                val emailError =
                    if (!state.email.contains("@")) "Enter a valid email" else null

                val passwordError =
                    if (state.password.length < 6) {
                        "Password must be at least 6 characters"
                    } else {
                        null
                    }

                val confirmError =
                    if (state.confirmPassword != state.password) {
                        "Passwords don't match"
                    } else {
                        null
                    }

                if (
                    listOf(
                        nameError,
                        emailError,
                        passwordError,
                        confirmError
                    ).any { it != null }
                ) {
                    _uiState.update {
                        it.copy(
                            nameError = nameError,
                            emailError = emailError,
                            passwordError = passwordError,
                            confirmPasswordError = confirmError
                        )
                    }
                    return
                }

                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true) }

                    try {
                        delay(1000.milliseconds)

                        sessionRepository.login(
                            name = state.name,
                            email = state.email
                        )

                        _events.send(RegisterEvent.NavigateHome)

                    } finally {
                        _uiState.update { it.copy(isLoading = false) }
                    }
                }
            }

            RegisterAction.GoogleSignUpClicked -> {
                viewModelScope.launch {
                    _events.send(
                        RegisterEvent.ShowError("Google Sign-In isn't wired up")
                    )
                }
            }

            RegisterAction.LoginLinkClicked -> {
                viewModelScope.launch {
                    _events.send(RegisterEvent.NavigateToLogin)
                }
            }
        }
    }
}