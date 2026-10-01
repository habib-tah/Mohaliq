package com.habib.mohaliq.feature.hotel

import com.habib.mohaliq.core.model.MapLocation

sealed interface HotelEvent {

    data object NavigateBack : HotelEvent

    data class NavigateBooking(val hotelId: String) : HotelEvent

    data class OpenMap(val location: MapLocation) : HotelEvent

}