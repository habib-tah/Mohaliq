package com.habib.mohaliq.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habib.mohaliq.core.data.repository.PlaceRepository
import com.habib.mohaliq.core.model.PlaceType
import com.habib.mohaliq.feature.search.model.SearchCategory
import com.habib.mohaliq.feature.search.model.SearchResultItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private fun PlaceType.toSearchCategory() = when (this) {
    PlaceType.HOTEL -> SearchCategory.HOTEL
    PlaceType.RESTAURANT -> SearchCategory.RESTAURANT
    PlaceType.DESTINATION -> SearchCategory.DESTINATION
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val initialCategory: SearchCategory = runCatching {
        SearchCategory.valueOf(savedStateHandle.get<String>("category") ?: "ALL")
    }.getOrDefault(SearchCategory.ALL)

    private val _uiState = MutableStateFlow(SearchUiState(selectedCategory = initialCategory))
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<SearchEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        observePlaces()
    }

    fun onAction(action: SearchAction) {
        when (action) {

            is SearchAction.QueryChanged -> {
                _uiState.update { it.copy(query = action.query) }
            }

            is SearchAction.CategorySelected -> {
                _uiState.update { it.copy(selectedCategory = action.category) }
            }

            is SearchAction.ResultClicked -> {

                val result = _uiState.value.results.find { it.item.id == action.itemId }
                    ?: return

                val type = when (result.category) {
                    SearchCategory.HOTEL -> PlaceType.HOTEL
                    SearchCategory.RESTAURANT -> PlaceType.RESTAURANT
                    SearchCategory.DESTINATION -> PlaceType.DESTINATION
                    SearchCategory.ALL -> return
                }

                viewModelScope.launch {
                    _events.send(SearchEvent.NavigateToDetail(action.itemId, type))
                }
            }

            is SearchAction.FavoriteClicked -> {
                viewModelScope.launch {
                    placeRepository.toggleFavorite(action.itemId)
                }
            }

            SearchAction.CloseClicked -> {
                viewModelScope.launch {
                    _events.send(SearchEvent.NavigateBack)
                }
            }

        }
    }

    private fun observePlaces() {
        viewModelScope.launch {
            placeRepository.observeAll().collect { places ->
                _uiState.update {
                    it.copy(
                        results = places.map { typedPlace ->
                            SearchResultItem(typedPlace.item, typedPlace.type.toSearchCategory())
                        }
                    )
                }
            }
        }
    }

}
