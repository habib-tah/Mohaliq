package com.habib.mohaliq.feature.auth

sealed interface RegisterEvent {

    data object NavigateHome : RegisterEvent

    data object NavigateToLogin : RegisterEvent

    data class ShowError(val message: String) : RegisterEvent

}
