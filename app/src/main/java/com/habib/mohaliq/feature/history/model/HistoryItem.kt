package com.habib.mohaliq.feature.history.model

import com.habib.mohaliq.core.model.PlaceType

data class HistoryItem(
    val id: String,
    val placeId: String,
    val placeType: PlaceType,
    val title: String,
    val location: String,
    val dateRange: String,
    val guestCount: Int,
    val price: Double,
    val status: HistoryStatus
)
