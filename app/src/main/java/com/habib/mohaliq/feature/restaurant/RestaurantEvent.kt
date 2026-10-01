package com.habib.mohaliq.feature.restaurant

import com.habib.mohaliq.core.model.MapLocation

sealed interface RestaurantEvent {

    data object NavigateBack : RestaurantEvent

    data class OpenMap(
        val location: MapLocation
    ) : RestaurantEvent

    data class NavigateToPayment(
        val restaurantId: String,
        val reservationDate: String,
        val partySize: Int
    ) : RestaurantEvent

    data class ShowError(
        val message: String
    ) : RestaurantEvent
}