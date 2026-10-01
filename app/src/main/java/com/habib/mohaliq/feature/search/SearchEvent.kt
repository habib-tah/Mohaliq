package com.habib.mohaliq.feature.search

import com.habib.mohaliq.core.model.PlaceType

sealed interface SearchEvent {

    data object NavigateBack : SearchEvent

    data class NavigateToDetail(val itemId: String, val type: PlaceType) : SearchEvent

}
