package com.habib.mohaliq.feature.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.habib.mohaliq.app.navigation.Destinations
import com.habib.mohaliq.core.model.PlaceType

@Composable
fun FavoritesRoute(

    viewModel: FavoritesViewModel = hiltViewModel(),

    onNavigateToDetail: (String, PlaceType) -> Unit = { _, _ -> },

    onBottomNavigation: (Destinations) -> Unit = {}

) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {

        viewModel.events.collect { event ->

            when (event) {

                is FavoritesEvent.NavigateToDetail -> onNavigateToDetail(event.itemId, event.type)

            }

        }

    }

    FavoritesScreen(
        uiState = uiState.value,
        onAction = viewModel::onAction,
        onBottomNavigation = onBottomNavigation
    )

}
