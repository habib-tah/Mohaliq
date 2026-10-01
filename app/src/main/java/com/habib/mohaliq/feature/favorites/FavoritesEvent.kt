package com.habib.mohaliq.feature.favorites

import com.habib.mohaliq.core.model.PlaceType

sealed interface FavoritesEvent {

    data class NavigateToDetail(val itemId: String, val type: PlaceType) : FavoritesEvent

}
