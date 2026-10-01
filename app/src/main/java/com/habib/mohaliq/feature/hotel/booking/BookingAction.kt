package com.habib.mohaliq.feature.hotel.booking

sealed interface BookingAction {

    data object BackClicked : BookingAction

    data object FavoriteClicked : BookingAction

    data object ShowMapClicked : BookingAction

    data object CheckInClicked : BookingAction

    data object CheckOutClicked : BookingAction

    data class CheckInSelected(
        val date: String,
        val millis: Long
    ) : BookingAction

    data class CheckOutSelected(
        val date: String,
        val millis: Long
    ) : BookingAction

    data object DatePickerDismissed : BookingAction

    data object GuestIncremented : BookingAction

    data object GuestDecremented : BookingAction

    data class RoomExpandToggled(val roomId: String) : BookingAction

    data class BookRoom(val roomId: String) : BookingAction

    data object ContinueClicked : BookingAction

}
