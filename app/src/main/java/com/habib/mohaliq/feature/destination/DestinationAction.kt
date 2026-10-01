package com.habib.mohaliq.feature.destination

sealed interface DestinationAction {

    data object BackClicked : DestinationAction

    data object FavoriteClicked : DestinationAction

    data object ShowMapClicked : DestinationAction

    data class NearbyItemClicked(val itemId: String) : DestinationAction

    data class NearbyFavoriteClicked(val itemId: String) : DestinationAction

    data object NearbySeeMoreClicked : DestinationAction

}
