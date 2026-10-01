package com.habib.mohaliq.feature.hotel.booking

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habib.mohaliq.core.data.repository.PlaceRepository
import com.habib.mohaliq.core.model.MapLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MAX_GUESTS = 10
private const val MIN_GUESTS = 1

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val hotelId: String = checkNotNull(savedStateHandle["hotelId"])

    private val _uiState = MutableStateFlow(
        BookingUiState(hotelId = hotelId)
    )
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<BookingEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        observeHotel()
        observeRooms()
    }

    fun onAction(action: BookingAction) {
        when (action) {

            BookingAction.BackClicked -> {
                viewModelScope.launch {
                    _events.send(BookingEvent.NavigateBack)
                }
            }

            BookingAction.FavoriteClicked -> {
                viewModelScope.launch {
                    placeRepository.toggleFavorite(hotelId)
                }
            }

            BookingAction.ShowMapClicked -> {
                val state = _uiState.value

                viewModelScope.launch {
                    _events.send(
                        BookingEvent.OpenMap(
                            MapLocation(
                                state.latitude,
                                state.longitude,
                                state.hotelName
                            )
                        )
                    )
                }
            }

            BookingAction.CheckInClicked -> {
                _uiState.update {
                    it.copy(showCheckInPicker = true)
                }
            }

            BookingAction.CheckOutClicked -> {
                _uiState.update {
                    it.copy(showCheckOutPicker = true)
                }
            }

            is BookingAction.CheckInSelected -> {
                _uiState.update { state ->

                    state.copy(
                        checkIn = action.date,
                        checkInMillis = action.millis,
                        checkOut = if (
                            state.checkOutMillis != null &&
                            state.checkOutMillis <= action.millis
                        ) {
                            ""
                        } else {
                            state.checkOut
                        },
                        checkOutMillis = if (
                            state.checkOutMillis != null &&
                            state.checkOutMillis <= action.millis
                        ) {
                            null
                        } else {
                            state.checkOutMillis
                        },
                        showCheckInPicker = false
                    )
                }
            }

            is BookingAction.CheckOutSelected -> {

                val checkInMillis = _uiState.value.checkInMillis

                if (checkInMillis == null || action.millis > checkInMillis) {

                    _uiState.update {
                        it.copy(
                            checkOut = action.date,
                            checkOutMillis = action.millis,
                            showCheckOutPicker = false
                        )
                    }
                }
            }

            BookingAction.DatePickerDismissed -> {
                _uiState.update {
                    it.copy(
                        showCheckInPicker = false,
                        showCheckOutPicker = false
                    )
                }
            }

            BookingAction.GuestIncremented -> {
                _uiState.update {
                    it.copy(
                        guestCount = (it.guestCount + 1)
                            .coerceAtMost(MAX_GUESTS)
                    )
                }
            }

            BookingAction.GuestDecremented -> {
                _uiState.update {
                    it.copy(
                        guestCount = (it.guestCount - 1)
                            .coerceAtLeast(MIN_GUESTS)
                    )
                }
            }

            is BookingAction.RoomExpandToggled -> {
                _uiState.update {
                    it.copy(
                        expandedRoomId =
                            if (it.expandedRoomId == action.roomId) {
                                null
                            } else {
                                action.roomId
                            }
                    )
                }
            }

            is BookingAction.BookRoom -> {
                _uiState.update {
                    it.copy(
                        selectedRoomId = action.roomId
                    )
                }
            }

            BookingAction.ContinueClicked -> {

                val state = _uiState.value
                val roomId = state.selectedRoomId

                if (roomId == null) {
                    return
                }

                if (!state.canContinue) {
                    viewModelScope.launch {
                        _events.send(
                            BookingEvent.ShowError(
                                "Please choose check-in and check-out dates"
                            )
                        )
                    }
                    return
                }

                viewModelScope.launch {
                    _events.send(
                        BookingEvent.NavigateToPayment(
                            hotelId = hotelId,
                            roomId = roomId,
                            checkIn = state.checkIn,
                            checkOut = state.checkOut,
                            guestCount = state.guestCount
                        )
                    )
                }
            }
        }
    }

    private fun observeHotel() {
        viewModelScope.launch {
            placeRepository.observeDetail(hotelId).collect { detail ->

                if (detail == null) return@collect

                _uiState.update {
                    it.copy(
                        hotelName = detail.title,
                        location = detail.location,
                        starLabel = detail.badge ?: "",
                        latitude = detail.latitude,
                        longitude = detail.longitude,
                        rating = detail.rating,
                        reviews = detail.reviewCount,
                        isFavorite = detail.isFavorite
                    )
                }
            }
        }
    }

    private fun observeRooms() {
        viewModelScope.launch {
            placeRepository.observeRoomOptions(hotelId).collect { rooms ->
                _uiState.update {
                    it.copy(rooms = rooms)
                }
            }
        }
    }
}