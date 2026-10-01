package com.habib.mohaliq.feature.hotel.booking

import com.habib.mohaliq.core.model.RoomOption

data class BookingUiState(

    val hotelId: String = "",

    val hotelName: String = "",

    val location: String = "",

    val starLabel: String = "",

    val latitude: Double = 0.0,

    val longitude: Double = 0.0,

    val rating: Float = 0f,

    val reviews: Int = 0,

    val isFavorite: Boolean = false,

    val checkIn: String = "",

    val checkOut: String = "",

    val checkInMillis: Long? = null,

    val checkOutMillis: Long? = null,

    val guestCount: Int = 1,

    val rooms: List<RoomOption> = emptyList(),

    val expandedRoomId: String? = null,

    val selectedRoomId: String? = null,

    val showCheckInPicker: Boolean = false,

    val showCheckOutPicker: Boolean = false,

    val isSubmitting: Boolean = false
) {

    val canContinue: Boolean
        get() = checkIn.isNotBlank() && checkOut.isNotBlank() && selectedRoomId != null

}
