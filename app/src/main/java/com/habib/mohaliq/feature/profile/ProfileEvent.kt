package com.habib.mohaliq.feature.profile

sealed interface ProfileEvent {

    data class NavigateToMenuItem(val itemId: String) : ProfileEvent

    data object NavigateToLogin : ProfileEvent

}
