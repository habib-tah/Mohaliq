package com.habib.mohaliq.feature.hotel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.habib.mohaliq.core.model.MapLocation

@Composable
fun HotelRoute(

    viewModel: HotelViewModel = hiltViewModel(),

    onNavigateBack: () -> Unit = {},

    onNavigateBooking: (String) -> Unit = {},

    onOpenMap: (MapLocation) -> Unit = {}

) {

    val uiState =
        viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {

        viewModel.events.collect { event ->

            when (event) {

                HotelEvent.NavigateBack -> {

                    onNavigateBack()

                }

                is HotelEvent.NavigateBooking -> {

                    onNavigateBooking(event.hotelId)

                }

                is HotelEvent.OpenMap -> {

                    onOpenMap(event.location)

                }

            }

        }

    }

    HotelScreen(

        uiState = uiState.value,

        onAction = viewModel::onAction

    )

}