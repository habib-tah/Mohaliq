package com.habib.mohaliq.core.model

data class BookingRecord(
    val id: Long,
    val placeId: String,
    val placeType: PlaceType,
    val title: String,
    val location: String,
    val dateRange: String,
    val guestCount: Int,
    val price: Double,
    val status: BookingStatus
)
