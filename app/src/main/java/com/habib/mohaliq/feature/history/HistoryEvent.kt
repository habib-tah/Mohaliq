package com.habib.mohaliq.feature.history

import com.habib.mohaliq.core.model.PlaceType

sealed interface HistoryEvent {

    data class NavigateToDetail(val placeId: String, val placeType: PlaceType) : HistoryEvent

}
