package com.habib.mohaliq.feature.profile

import com.habib.mohaliq.feature.profile.model.ProfileMenuItem

data class ProfileUiState(
    val userName: String = "",
    val userEmail: String = "",
    val menuItems: List<ProfileMenuItem> = emptyList(),
    val selectedBottomBarIndex: Int = 4,
    val showLogoutConfirmation: Boolean = false
)
