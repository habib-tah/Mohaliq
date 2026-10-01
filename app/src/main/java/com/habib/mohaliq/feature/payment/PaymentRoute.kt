package com.habib.mohaliq.feature.payment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PaymentRoute(
    viewModel: PaymentViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onNavigateHome: () -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {

                PaymentEvent.NavigateBack -> {
                    onBack()
                }

                PaymentEvent.NavigateHome -> {
                    onNavigateHome()
                }
            }
        }
    }

    PaymentScreen(
        uiState = uiState.value,
        onAction = viewModel::onAction
    )
}