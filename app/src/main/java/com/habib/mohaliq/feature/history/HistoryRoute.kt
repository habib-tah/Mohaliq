package com.habib.mohaliq.feature.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.habib.mohaliq.app.navigation.Destinations
import com.habib.mohaliq.core.model.PlaceType

@Composable
fun HistoryRoute(

    viewModel: HistoryViewModel = hiltViewModel(),

    onNavigateToDetail: (String, PlaceType) -> Unit = { _, _ -> },

    onBottomNavigation: (Destinations) -> Unit = {}

) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {

        viewModel.events.collect { event ->

            when (event) {

                is HistoryEvent.NavigateToDetail -> onNavigateToDetail(event.placeId, event.placeType)

            }

        }

    }

    HistoryScreen(
        uiState = uiState.value,
        onAction = viewModel::onAction,
        onBottomNavigation = onBottomNavigation
    )

}
