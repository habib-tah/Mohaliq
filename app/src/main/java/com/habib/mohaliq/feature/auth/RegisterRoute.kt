package com.habib.mohaliq.feature.auth

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RegisterRoute(
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                RegisterEvent.NavigateHome -> onNavigateHome()

                RegisterEvent.NavigateToLogin -> onNavigateToLogin()

                is RegisterEvent.ShowError -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    RegisterScreen(
        uiState = uiState.value,
        onAction = viewModel::onAction,
        snackbarHostState = snackbarHostState
    )
}