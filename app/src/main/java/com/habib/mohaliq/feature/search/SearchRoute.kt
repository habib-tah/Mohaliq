package com.habib.mohaliq.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.habib.mohaliq.core.model.PlaceType

@Composable
fun SearchRoute(

    viewModel: SearchViewModel = hiltViewModel(),

    onBack: () -> Unit = {},

    onNavigateToDetail: (String, PlaceType) -> Unit = { _, _ -> }

) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {

        viewModel.events.collect { event ->

            when (event) {

                SearchEvent.NavigateBack -> onBack()

                is SearchEvent.NavigateToDetail -> onNavigateToDetail(event.itemId, event.type)

            }

        }

    }

    SearchScreen(
        uiState = uiState.value,
        onAction = viewModel::onAction
    )

}
