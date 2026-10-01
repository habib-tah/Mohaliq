package com.habib.mohaliq.feature.hotel

sealed interface HotelAction {

    data object BackClicked : HotelAction

    data object FavoriteClicked : HotelAction

    data object ShareClicked : HotelAction

    data object ShowMapClicked : HotelAction

    data class RoomSelected(
        val roomId: String
    ) : HotelAction

    data object BookingClicked : HotelAction

}