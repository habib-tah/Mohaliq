package com.habib.mohaliq.feature.home

sealed interface HomeEvent {
    data class NavigateToDetail(val itemId: String) : HomeEvent
    data class NavigateToCategory(val categoryId: String) : HomeEvent
    data object NavigateToSearch : HomeEvent
    data object NavigateToAiChat : HomeEvent
}