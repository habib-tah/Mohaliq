package com.habib.mohaliq.feature.auth

sealed interface RegisterAction {

    data class NameChanged(val value: String) : RegisterAction

    data class EmailChanged(val value: String) : RegisterAction

    data class PasswordChanged(val value: String) : RegisterAction

    data class ConfirmPasswordChanged(val value: String) : RegisterAction

    data object RegisterClicked : RegisterAction

    data object GoogleSignUpClicked : RegisterAction

    data object LoginLinkClicked : RegisterAction

}
