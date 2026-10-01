package com.habib.mohaliq.feature.restaurant

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.habib.mohaliq.core.model.MapLocation

@Composable
fun RestaurantRoute(
    viewModel: RestaurantViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onOpenMap: (MapLocation) -> Unit = {},
    onNavigateToPayment: (
        restaurantId: String,
        reservationDate: String,
        partySize: Int
    ) -> Unit = { _, _, _ -> }
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->

            when (event) {

                RestaurantEvent.NavigateBack -> {
                    onBack()
                }

                is RestaurantEvent.OpenMap -> {
                    onOpenMap(event.location)
                }

                is RestaurantEvent.NavigateToPayment -> {
                    onNavigateToPayment(
                        event.restaurantId,
                        event.reservationDate,
                        event.partySize
                    )
                }

                is RestaurantEvent.ShowError -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    RestaurantScreen(
        uiState = uiState.value,
        onAction = viewModel::onAction,
        snackbarHostState = snackbarHostState
    )
}