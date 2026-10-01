package com.habib.mohaliq.feature.ai

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AiChatRoute(

    viewModel: AiChatViewModel = hiltViewModel(),

    onBack: () -> Unit = {}

) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {

        viewModel.events.collect { event ->

            when (event) {

                AiChatEvent.NavigateBack -> onBack()

            }

        }

    }

    AiChatScreen(
        uiState = uiState.value,
        onAction = viewModel::onAction
    )

}
