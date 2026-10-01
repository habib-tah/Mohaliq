package com.habib.mohaliq.core.model

data class RoomOption(
    val id: String,
    val hotelId: String,
    val title: String,
    val price: Double,
    val breakfastIncluded: Boolean = false,
    val cancellationPolicy: String,
    val imageUrl: String
)
