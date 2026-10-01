package com.habib.mohaliq.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habib.mohaliq.core.data.repository.PlaceRepository
import com.habib.mohaliq.core.model.PlaceType
import com.habib.mohaliq.feature.favorites.model.FavoriteCategory
import com.habib.mohaliq.feature.favorites.model.FavoriteItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private fun PlaceType.toFavoriteCategory() = when (this) {
    PlaceType.HOTEL -> FavoriteCategory.HOTEL
    PlaceType.RESTAURANT -> FavoriteCategory.RESTAURANT
    PlaceType.DESTINATION -> FavoriteCategory.DESTINATION
}

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val placeRepository: PlaceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<FavoritesEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        observeFavorites()
    }

    fun onAction(action: FavoritesAction) {
        when (action) {

            is FavoritesAction.CategorySelected -> {
                _uiState.update { it.copy(selectedCategory = action.category) }
            }

            is FavoritesAction.ItemClicked -> {

                val favorite = _uiState.value.items.find { it.item.id == action.itemId }
                    ?: return

                val type = when (favorite.category) {
                    FavoriteCategory.HOTEL -> PlaceType.HOTEL
                    FavoriteCategory.RESTAURANT -> PlaceType.RESTAURANT
                    FavoriteCategory.DESTINATION -> PlaceType.DESTINATION
                    FavoriteCategory.ALL -> return
                }

                viewModelScope.launch {
                    _events.send(FavoritesEvent.NavigateToDetail(action.itemId, type))
                }

            }

            is FavoritesAction.FavoriteRemoved -> {
                viewModelScope.launch {
                    placeRepository.toggleFavorite(action.itemId)
                }
            }

            is FavoritesAction.BottomBarItemSelected -> {
                _uiState.update { it.copy(selectedBottomBarIndex = action.index) }
            }

        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            placeRepository.observeFavorites().collect { favorites ->
                _uiState.update {
                    it.copy(
                        items = favorites.map { typedPlace ->
                            FavoriteItem(typedPlace.item, typedPlace.type.toFavoriteCategory())
                        }
                    )
                }
            }
        }
    }

}
