package com.habib.mohaliq.feature.hotel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habib.mohaliq.R
import com.habib.mohaliq.core.data.repository.PlaceRepository
import com.habib.mohaliq.core.model.MapLocation
import com.habib.mohaliq.feature.hotel.model.FacilityItem
import com.habib.mohaliq.feature.hotel.model.RoomItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val hotelId: String = checkNotNull(savedStateHandle["hotelId"])

    private val _uiState = MutableStateFlow(HotelUiState(hotelId = hotelId))
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<HotelEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        loadFacilities()
        observeHotel()
        observeRooms()
    }

    fun onAction(action: HotelAction) {
        when (action) {

            HotelAction.BackClicked -> {
                viewModelScope.launch {
                    _events.send(HotelEvent.NavigateBack)
                }
            }

            HotelAction.FavoriteClicked -> {
                viewModelScope.launch {
                    placeRepository.toggleFavorite(hotelId)
                }
            }

            HotelAction.ShareClicked -> {
                // No share target wired up yet — nothing to do.
            }

            HotelAction.ShowMapClicked -> {
                val state = _uiState.value
                viewModelScope.launch {
                    _events.send(
                        HotelEvent.OpenMap(
                            MapLocation(state.latitude, state.longitude, state.hotelName)
                        )
                    )
                }
            }

            is HotelAction.RoomSelected -> {
                _uiState.update { state ->
                    state.copy(
                        rooms = state.rooms.map {
                            it.copy(selected = it.id == action.roomId)
                        }
                    )
                }
            }

            HotelAction.BookingClicked -> {
                viewModelScope.launch {
                    _events.send(HotelEvent.NavigateBooking(hotelId))
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
                        overview = detail.description,
                        rating = detail.rating,
                        reviews = detail.reviewCount,
                        starLabel = detail.badge ?: "",
                        imageUrl = detail.imageUrl,
                        latitude = detail.latitude,
                        longitude = detail.longitude,
                        favorite = detail.isFavorite,
                        isFavorite = detail.isFavorite
                    )
                }

            }
        }
    }

    private fun observeRooms() {
        viewModelScope.launch {
            placeRepository.observeRoomOptions(hotelId).collect { rooms ->
                _uiState.update { state ->
                    state.copy(
                        rooms = rooms.map { room ->
                            RoomItem(
                                id = room.id,
                                title = room.title,
                                selected = state.rooms.find { it.id == room.id }?.selected ?: false
                            )
                        }
                    )
                }
            }
        }
    }

    private fun loadFacilities() {

        _uiState.update {

            it.copy(
                facilities = listOf(
                    FacilityItem("wifi", R.drawable.ic_wifi_outline, "Free WiFi"),
                    FacilityItem("pool", R.drawable.ic_bubble_outline, "Swimming Pool"),
                    FacilityItem("parking", R.drawable.ic_smart_car_outline, "Free Parking"),
                    FacilityItem("breakfast", R.drawable.ic_coffee_two_tone, "Breakfast")
                )
            )

        }

    }

}
