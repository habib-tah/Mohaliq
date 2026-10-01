package com.habib.mohaliq.feature.search

import com.habib.mohaliq.feature.search.model.SearchCategory

sealed interface SearchAction {

    data class QueryChanged(val query: String) : SearchAction

    data class CategorySelected(val category: SearchCategory) : SearchAction

    data class ResultClicked(val itemId: String) : SearchAction

    data class FavoriteClicked(val itemId: String) : SearchAction

    data object CloseClicked : SearchAction

}
