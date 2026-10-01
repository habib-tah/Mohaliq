package com.habib.mohaliq.feature.auth

sealed interface LoginAction {

    data class EmailChanged(val value: String) : LoginAction

    data class PasswordChanged(val value: String) : LoginAction

    data object LoginClicked : LoginAction

    data object GoogleSignInClicked : LoginAction

    data object RegisterLinkClicked : LoginAction

}
