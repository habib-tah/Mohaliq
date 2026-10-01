package com.habib.mohaliq.feature.hotel.booking

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.habib.mohaliq.core.model.MapLocation

@Composable
fun BookingRoute(
    viewModel: BookingViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onOpenMap: (MapLocation) -> Unit = {},
    onNavigateToPayment: (
        hotelId: String,
        roomId: String,
        checkIn: String,
        checkOut: String,
        guestCount: Int
    ) -> Unit = { _, _, _, _, _ -> }
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {

                BookingEvent.NavigateBack -> {
                    onBack()
                }

                is BookingEvent.OpenMap -> {
                    onOpenMap(event.location)
                }

                is BookingEvent.NavigateToPayment -> {
                    onNavigateToPayment(
                        event.hotelId,
                        event.roomId,
                        event.checkIn,
                        event.checkOut,
                        event.guestCount
                    )
                }

                is BookingEvent.ShowError -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    BookingScreen(
        uiState = uiState.value,
        onAction = viewModel::onAction,
        snackbarHostState = snackbarHostState
    )
}