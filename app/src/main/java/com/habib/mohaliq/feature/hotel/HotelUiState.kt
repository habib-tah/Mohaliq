package com.habib.mohaliq.feature.hotel

import com.habib.mohaliq.feature.hotel.model.FacilityItem
import com.habib.mohaliq.feature.hotel.model.RoomItem

data class HotelUiState(

    val hotelId: String = "",

    val hotelName: String = "",

    val location: String = "",

    val overview: String = "",

    val isFavorite: Boolean = false,

    val rating: Float = 0f,

    val reviews: Int = 0,

    val starLabel: String = "",

    val imageUrl: String = "",

    val latitude: Double = 0.0,

    val longitude: Double = 0.0,

    val facilities: List<FacilityItem> = emptyList(),

    val rooms: List<RoomItem> = emptyList(),

    val favorite: Boolean = false

)