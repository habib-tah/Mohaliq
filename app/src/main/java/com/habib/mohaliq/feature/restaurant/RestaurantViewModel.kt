package com.habib.mohaliq.feature.restaurant

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

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val restaurantId: String = checkNotNull(savedStateHandle["restaurantId"])

    private val _uiState = MutableStateFlow(RestaurantUiState(restaurantId = restaurantId))
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<RestaurantEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        observeRestaurant()
        observeMenu()
    }

    fun onAction(action: RestaurantAction) {
        when (action) {

            RestaurantAction.BackClicked -> {
                viewModelScope.launch {
                    _events.send(RestaurantEvent.NavigateBack)
                }
            }

            RestaurantAction.FavoriteClicked -> {
                viewModelScope.launch {
                    placeRepository.toggleFavorite(restaurantId)
                }
            }

            RestaurantAction.ShowMapClicked -> {
                val state = _uiState.value
                viewModelScope.launch {
                    _events.send(
                        RestaurantEvent.OpenMap(
                            MapLocation(state.latitude, state.longitude, state.restaurantName)
                        )
                    )
                }
            }

            RestaurantAction.DateFieldClicked -> {
                _uiState.update { it.copy(showDatePicker = true) }
            }

            is RestaurantAction.DateSelected -> {
                _uiState.update {
                    it.copy(reservationDate = action.date, showDatePicker = false)
                }
            }

            RestaurantAction.DatePickerDismissed -> {
                _uiState.update { it.copy(showDatePicker = false) }
            }

            RestaurantAction.PartySizeIncremented -> {
                _uiState.update {
                    it.copy(partySize = (it.partySize + 1).coerceAtMost(12))
                }
            }

            RestaurantAction.PartySizeDecremented -> {
                _uiState.update {
                    it.copy(partySize = (it.partySize - 1).coerceAtLeast(1))
                }
            }

            RestaurantAction.ReserveClicked -> {

                val state = _uiState.value

                if (state.reservationDate.isBlank()) {
                    viewModelScope.launch {
                        _events.send(
                            RestaurantEvent.ShowError(
                                "Please choose a reservation date"
                            )
                        )
                    }
                    return
                }

                viewModelScope.launch {
                    _events.send(
                        RestaurantEvent.NavigateToPayment(
                            restaurantId = restaurantId,
                            reservationDate = state.reservationDate,
                            partySize = state.partySize
                        )
                    )
                }
            }

        }
    }

    private fun observeRestaurant() {
        viewModelScope.launch {
            placeRepository.observeDetail(restaurantId).collect { detail ->

                if (detail == null) return@collect

                _uiState.update {
                    it.copy(
                        restaurantName = detail.title,
                        location = detail.location,
                        overview = detail.description,
                        rating = detail.rating,
                        reviews = detail.reviewCount,
                        imageUrl = detail.imageUrl,
                        latitude = detail.latitude,
                        longitude = detail.longitude,
                        isFavorite = detail.isFavorite
                    )
                }

            }
        }
    }

    private fun observeMenu() {
        viewModelScope.launch {
            placeRepository.observeMenuItems(restaurantId).collect { items ->
                _uiState.update { it.copy(menuItems = items) }
            }
        }
    }

}
