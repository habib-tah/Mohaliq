package com.habib.mohaliq.feature.explore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.habib.mohaliq.app.navigation.Destinations
import com.habib.mohaliq.core.model.PlaceType

@Composable
fun ExploreRoute(
    viewModel: ExploreViewModel = hiltViewModel(),
    onNavigateToDetail: (String, PlaceType) -> Unit = { _, _ -> },
    onNavigateToSection: (String) -> Unit = {},
    onChangeLocation: () -> Unit = {},
    onBottomNavigation: (Destinations) -> Unit = {}

) {

    val uiState =
        viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {

        viewModel.events.collect {

            when (it) {

                ExploreEvent.ChangeLocation -> {

                    onChangeLocation()

                }

                is ExploreEvent.NavigateToDetail -> {

                    onNavigateToDetail(
                        it.id,
                        it.type
                    )

                }

                is ExploreEvent.NavigateToSection -> {

                    onNavigateToSection(
                        it.section
                    )

                }

            }

        }

    }

    ExploreScreen(

        uiState = uiState.value,

        onAction = viewModel::onAction,
        onBottomNavigation = onBottomNavigation

    )

}