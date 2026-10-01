package com.habib.mohaliq.feature.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.habib.mohaliq.app.navigation.Destinations

@Composable
fun ProfileRoute(

    viewModel: ProfileViewModel = hiltViewModel(),

    onNavigateToMenuItem: (String) -> Unit = {},

    onNavigateToLogin: () -> Unit = {},

    onBottomNavigation: (Destinations) -> Unit = {}

) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {

        viewModel.events.collect { event ->

            when (event) {

                is ProfileEvent.NavigateToMenuItem -> onNavigateToMenuItem(event.itemId)

                ProfileEvent.NavigateToLogin -> onNavigateToLogin()

            }

        }

    }

    ProfileScreen(
        uiState = uiState.value,
        onAction = viewModel::onAction,
        onBottomNavigation = onBottomNavigation
    )

}
