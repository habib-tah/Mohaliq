package com.habib.mohaliq.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.habib.mohaliq.app.navigation.Destinations

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit = {},
    onNavigateToCategory: (String) -> Unit = {},
    onNavigateToSearch: () -> Unit = {},
    onNavigateToAiChat: () -> Unit = {},
    onBottomNavigation: (Destinations) -> Unit = {}
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {

        viewModel.events.collect { event ->

            when (event) {

                is HomeEvent.NavigateToDetail ->
                    onNavigateToDetail(event.itemId)

                is HomeEvent.NavigateToCategory ->
                    onNavigateToCategory(event.categoryId)

                HomeEvent.NavigateToSearch ->
                    onNavigateToSearch()

                HomeEvent.NavigateToAiChat ->
                    onNavigateToAiChat()
            }
        }
    }

    HomeScreen(
        uiState = uiState.value,
        onAction = viewModel::onAction,
        onBottomNavigation = onBottomNavigation
    )
}