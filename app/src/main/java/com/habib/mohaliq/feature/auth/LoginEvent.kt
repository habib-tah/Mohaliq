package com.habib.mohaliq.feature.auth

sealed interface LoginEvent {

    data object NavigateHome : LoginEvent

    data object NavigateToRegister : LoginEvent

    data class ShowError(val message: String) : LoginEvent

}
