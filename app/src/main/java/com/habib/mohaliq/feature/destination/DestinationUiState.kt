package com.habib.mohaliq.feature.destination

import com.habib.mohaliq.core.model.TravelCardItem

data class DestinationUiState(
    val destinationId: String = "",
    val name: String = "",
    val location: String = "",
    val rating: Float = 0f,
    val reviews: Int = 0,
    val overview: String = "",
    val isFavorite: Boolean = false,
    val imageUrl: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val nearbyPlaces: List<TravelCardItem> = emptyList()
)
