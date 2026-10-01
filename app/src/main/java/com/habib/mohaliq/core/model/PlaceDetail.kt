package com.habib.mohaliq.core.model

data class PlaceDetail(
    val id: String,
    val type: PlaceType,
    val title: String,
    val location: String,
    val description: String,
    val rating: Float,
    val reviewCount: Int,
    val currentPrice: Double,
    val originalPrice: Double? = null,
    val badge: String? = null,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double,
    val isFavorite: Boolean = false
) {

    fun toTravelCardItem() = TravelCardItem(
        id = id,
        title = title,
        location = location,
        rating = rating,
        reviewCount = reviewCount,
        currentPrice = currentPrice,
        originalPrice = originalPrice,
        badge = badge,
        isFavorite = isFavorite,
        imageUrl = imageUrl
    )

}
