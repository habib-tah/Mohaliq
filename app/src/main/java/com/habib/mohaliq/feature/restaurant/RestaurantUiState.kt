package com.habib.mohaliq.feature.restaurant

import com.habib.mohaliq.core.model.MenuItem

data class RestaurantUiState(

    val restaurantId: String = "",

    val restaurantName: String = "",

    val location: String = "",

    val overview: String = "",

    val rating: Float = 0f,

    val reviews: Int = 0,

    val isFavorite: Boolean = false,

    val imageUrl: String = "",

    val latitude: Double = 0.0,

    val longitude: Double = 0.0,

    val menuItems: List<MenuItem> = emptyList(),

    val partySize: Int = 2,

    val reservationDate: String = "",

    val showDatePicker: Boolean = false

)
