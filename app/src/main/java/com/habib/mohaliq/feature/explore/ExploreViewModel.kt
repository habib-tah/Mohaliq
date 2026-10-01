package com.habib.mohaliq.feature.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habib.mohaliq.core.data.repository.PlaceRepository
import com.habib.mohaliq.core.model.PlaceType
import com.habib.mohaliq.core.model.PromotionBanner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val placeRepository: PlaceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ExploreUiState(
            banner = PromotionBanner(
                id = "banner_1",
                title = "Discover Bali's\nHidden Gems",
                subtitle = "Explore unforgettable places and experiences."
            )
        )
    )

    val uiState = _uiState.asStateFlow()

    private val _events = Channel<ExploreEvent>(Channel.BUFFERED)

    val events = _events.receiveAsFlow()

    init {
        observePlaces()
    }

    fun onAction(
        action: ExploreAction
    ) {

        when (action) {

            ExploreAction.ChangeLocationClicked -> {

                viewModelScope.launch {

                    _events.send(
                        ExploreEvent.ChangeLocation
                    )

                }

            }

            is ExploreAction.BottomBarSelected -> {

                _uiState.update {

                    it.copy(
                        selectedBottomBar = action.index
                    )

                }

            }

            is ExploreAction.PopularClicked -> {

                navigate(action.itemId, PlaceType.DESTINATION)

            }

            is ExploreAction.FrequentlyVisitedClicked -> {

                navigate(action.itemId, PlaceType.RESTAURANT)

            }

            is ExploreAction.NearestHotelClicked -> {

                navigate(action.itemId, PlaceType.HOTEL)

            }

            is ExploreAction.FavoriteClicked -> {

                toggleFavorite(action.itemId)

            }

            ExploreAction.PopularSeeMoreClicked -> {

                navigateSection("popular")

            }

            ExploreAction.FrequentlyVisitedSeeMoreClicked -> {

                navigateSection("frequently")

            }

            ExploreAction.NearestHotelSeeMoreClicked -> {

                navigateSection("nearest")

            }

        }

    }

    private fun navigate(
        id: String,
        type: PlaceType
    ) {

        viewModelScope.launch {

            _events.send(
                ExploreEvent.NavigateToDetail(id, type)
            )

        }

    }

    private fun navigateSection(
        id: String
    ) {

        viewModelScope.launch {

            _events.send(
                ExploreEvent.NavigateToSection(id)
            )

        }

    }

    private fun toggleFavorite(
        id: String
    ) {

        viewModelScope.launch {
            placeRepository.toggleFavorite(id)
        }

    }

    private fun observePlaces() {

        viewModelScope.launch {
            placeRepository.observeByType(PlaceType.DESTINATION).collect { destinations ->
                _uiState.update { it.copy(popular = destinations) }
            }
        }

        viewModelScope.launch {
            placeRepository.observeByType(PlaceType.RESTAURANT).collect { restaurants ->
                _uiState.update { it.copy(frequentlyVisited = restaurants) }
            }
        }

        viewModelScope.launch {
            placeRepository.observeByType(PlaceType.HOTEL).collect { hotels ->
                _uiState.update { it.copy(nearestHotels = hotels) }
            }
        }

    }

}
