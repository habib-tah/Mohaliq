package com.habib.mohaliq.feature.destination

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habib.mohaliq.core.data.repository.PlaceRepository
import com.habib.mohaliq.core.model.MapLocation
import com.habib.mohaliq.core.model.PlaceType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DestinationViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val destinationId: String = checkNotNull(savedStateHandle["destinationId"])

    private val _uiState = MutableStateFlow(DestinationUiState(destinationId = destinationId))
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<DestinationEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        observeDestination()
        observeNearby()
    }

    fun onAction(action: DestinationAction) {
        when (action) {

            DestinationAction.BackClicked -> {
                viewModelScope.launch {
                    _events.send(DestinationEvent.NavigateBack)
                }
            }

            DestinationAction.FavoriteClicked -> {
                viewModelScope.launch {
                    placeRepository.toggleFavorite(destinationId)
                }
            }

            is DestinationAction.NearbyFavoriteClicked -> {
                viewModelScope.launch {
                    placeRepository.toggleFavorite(action.itemId)
                }
            }

            DestinationAction.NearbySeeMoreClicked -> {
                viewModelScope.launch {
                    _events.send(DestinationEvent.NavigateToSearch)
                }
            }

            DestinationAction.ShowMapClicked -> {
                val state = _uiState.value
                viewModelScope.launch {
                    _events.send(
                        DestinationEvent.OpenMap(
                            MapLocation(state.latitude, state.longitude, state.name)
                        )
                    )
                }
            }

            is DestinationAction.NearbyItemClicked -> {
                viewModelScope.launch {
                    _events.send(DestinationEvent.NavigateToDetail(action.itemId))
                }
            }

        }
    }

    private fun observeDestination() {
        viewModelScope.launch {
            placeRepository.observeDetail(destinationId).collect { detail ->

                if (detail == null) return@collect

                _uiState.update {
                    it.copy(
                        name = detail.title,
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

    private fun observeNearby() {
        viewModelScope.launch {
            placeRepository.observeByType(PlaceType.DESTINATION).collect { destinations ->
                _uiState.update {
                    it.copy(nearbyPlaces = destinations.filterNot { d -> d.id == destinationId }.take(3))
                }
            }
        }
    }

}
