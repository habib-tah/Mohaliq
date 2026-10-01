package com.habib.mohaliq.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habib.mohaliq.R
import com.habib.mohaliq.core.data.repository.PlaceRepository
import com.habib.mohaliq.core.model.PlaceType
import com.habib.mohaliq.feature.home.model.CategoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val placeRepository: PlaceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<HomeEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        loadCategories()
        observeHotels()
    }

    fun onAction(action: HomeAction) {
        when (action) {

            is HomeAction.SearchQueryChanged -> {
                _uiState.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is HomeAction.CategoryClicked -> {
                navigateToCategory(action.category.id)
            }

            is HomeAction.PopularClicked -> {
                navigateToDetail(action.item.id)
            }

            is HomeAction.RecommendationClicked -> {
                navigateToDetail(action.item.id)
            }

            is HomeAction.FavoriteClicked -> {
                toggleFavorite(action.itemId)
            }

            is HomeAction.BottomBarItemSelected -> {
                _uiState.update {
                    it.copy(selectedBottomBarIndex = action.index)
                }
            }

            HomeAction.PopularSeeMoreClicked -> {
                navigateToCategory("popular")
            }

            HomeAction.RecommendationSeeMoreClicked -> {
                navigateToCategory("recommendation")
            }

            HomeAction.SearchBarClicked -> {
                viewModelScope.launch {
                    _events.send(HomeEvent.NavigateToSearch)
                }
            }

            HomeAction.AiAssistantClicked -> {
                viewModelScope.launch {
                    _events.send(HomeEvent.NavigateToAiChat)
                }
            }
        }
    }

    private fun navigateToDetail(id: String) {
        viewModelScope.launch {
            _events.send(HomeEvent.NavigateToDetail(id))
        }
    }

    private fun navigateToCategory(id: String) {
        viewModelScope.launch {
            _events.send(HomeEvent.NavigateToCategory(id))
        }
    }

    private fun toggleFavorite(itemId: String) {
        viewModelScope.launch {
            placeRepository.toggleFavorite(itemId)
        }
    }

    private fun observeHotels() {
        viewModelScope.launch {
            placeRepository.observeByType(PlaceType.HOTEL).collect { hotels ->
                _uiState.update {
                    it.copy(
                        popularItems = hotels.take(2),
                        recommendationItems = hotels.drop(2)
                    )
                }
            }
        }
    }

    private fun loadCategories() {

        _uiState.update {

            it.copy(

                categories = listOf(

                    CategoryItem(
                        "hotel",
                        R.drawable.ic_buildings_two_tone,
                        R.string.category_hotel
                    ),

                    CategoryItem(
                        "restaurant",
                        R.drawable.ic_coffee_two_tone,
                        R.string.category_restaurant
                    ),

                    CategoryItem(
                        "tour",
                        R.drawable.ic_signpost_two_tone,
                        R.string.category_tour
                    ),

                    CategoryItem(
                        "guide",
                        R.drawable.ic_user_tick_two_tone,
                        R.string.category_tour_guide
                    ),

                    CategoryItem(
                        "more",
                        R.drawable.ic_more_two_tone,
                        R.string.category_more
                    )

                )

            )

        }

    }

}
