package com.habib.mohaliq.feature.profile

sealed interface ProfileAction {

    data class MenuItemClicked(val itemId: String) : ProfileAction

    data object LogoutClicked : ProfileAction

    data object LogoutConfirmed : ProfileAction

    data object LogoutDismissed : ProfileAction

    data class BottomBarItemSelected(val index: Int) : ProfileAction

}
