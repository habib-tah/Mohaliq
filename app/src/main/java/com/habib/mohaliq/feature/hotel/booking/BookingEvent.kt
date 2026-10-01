package com.habib.mohaliq.feature.hotel.booking

import com.habib.mohaliq.core.model.MapLocation

sealed interface BookingEvent {

    data object NavigateBack : BookingEvent

    data class OpenMap(val location: MapLocation) : BookingEvent

    data class NavigateToPayment(
        val hotelId: String,
        val roomId: String,
        val checkIn: String,
        val checkOut: String,
        val guestCount: Int
    ) : BookingEvent

    data class ShowError(val message: String) : BookingEvent

}
