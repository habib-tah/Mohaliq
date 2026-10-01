package com.habib.mohaliq.feature.explore

import com.habib.mohaliq.core.model.PlaceType

sealed interface ExploreEvent {

    data object ChangeLocation : ExploreEvent

    data class NavigateToDetail(
        val id: String,
        val type: PlaceType
    ) : ExploreEvent

    data class NavigateToSection(
        val section: String
    ) : ExploreEvent
}
