package com.habib.mohaliq.feature.destination

import com.habib.mohaliq.core.model.MapLocation

sealed interface DestinationEvent {

    data object NavigateBack : DestinationEvent

    data class OpenMap(val location: MapLocation) : DestinationEvent

    data class NavigateToDetail(val itemId: String) : DestinationEvent

    data object NavigateToSearch : DestinationEvent

}
