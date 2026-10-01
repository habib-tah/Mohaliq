package com.habib.mohaliq.feature.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.habib.mohaliq.core.model.MapLocation

@Composable
fun DestinationRoute(

    viewModel: DestinationViewModel = hiltViewModel(),

    onBack: () -> Unit = {},

    onOpenMap: (MapLocation) -> Unit = {},

    onNavigateToDetail: (String) -> Unit = {},

    onNavigateToSearch: () -> Unit = {}

) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {

        viewModel.events.collect { event ->

            when (event) {

                DestinationEvent.NavigateBack -> onBack()

                is DestinationEvent.OpenMap -> onOpenMap(event.location)

                is DestinationEvent.NavigateToDetail -> onNavigateToDetail(event.itemId)

                DestinationEvent.NavigateToSearch -> onNavigateToSearch()

            }

        }

    }

    DestinationScreen(
        uiState = uiState.value,
        onAction = viewModel::onAction
    )

}
