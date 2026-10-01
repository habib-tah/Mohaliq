package com.habib.mohaliq.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habib.mohaliq.R
import com.habib.mohaliq.core.data.repository.SessionRepository
import com.habib.mohaliq.feature.profile.model.ProfileMenuItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProfileUiState(
            menuItems = listOf(

                ProfileMenuItem(
                    "address",
                    R.string.profile_manage_address,
                    R.drawable.ic_pin
                ),

                ProfileMenuItem(
                    "notifications",
                    R.string.profile_notification_settings,
                    R.drawable.ic_notification_outline
                ),

                ProfileMenuItem(
                    "payment",
                    R.string.profile_payment_detail,
                    R.drawable.ic_card_payment_outline
                ),

                ProfileMenuItem(
                    "about",
                    R.string.profile_about,
                    R.drawable.ic_note_outline
                ),

                ProfileMenuItem(
                    "terms",
                    R.string.profile_terms,
                    R.drawable.ic_checklist
                ),

                ProfileMenuItem(
                    "privacy",
                    R.string.profile_privacy,
                    R.drawable.ic_shield_outline
                ),

                ProfileMenuItem(
                    "logout",
                    R.string.profile_logout,
                    R.drawable.ic_logout_outline,
                    isDestructive = true
                )

            )
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<ProfileEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            sessionRepository.session.collect { session ->
                _uiState.update { it.copy(userName = session.name, userEmail = session.email) }
            }
        }
    }

    fun onAction(action: ProfileAction) {
        when (action) {

            is ProfileAction.MenuItemClicked -> {

                if (action.itemId == "logout") {
                    _uiState.update { it.copy(showLogoutConfirmation = true) }
                } else {
                    viewModelScope.launch {
                        _events.send(ProfileEvent.NavigateToMenuItem(action.itemId))
                    }
                }

            }

            ProfileAction.LogoutClicked -> {
                _uiState.update { it.copy(showLogoutConfirmation = true) }
            }

            ProfileAction.LogoutConfirmed -> {
                _uiState.update { it.copy(showLogoutConfirmation = false) }
                viewModelScope.launch {
                    sessionRepository.logout()
                    _events.send(ProfileEvent.NavigateToLogin)
                }
            }

            ProfileAction.LogoutDismissed -> {
                _uiState.update { it.copy(showLogoutConfirmation = false) }
            }

            is ProfileAction.BottomBarItemSelected -> {
                _uiState.update { it.copy(selectedBottomBarIndex = action.index) }
            }

        }
    }

}
