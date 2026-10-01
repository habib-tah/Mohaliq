package com.habib.mohaliq.feature.search

import com.habib.mohaliq.feature.search.model.SearchCategory
import com.habib.mohaliq.feature.search.model.SearchResultItem

data class SearchUiState(
    val query: String = "",
    val selectedCategory: SearchCategory = SearchCategory.ALL,
    val results: List<SearchResultItem> = emptyList()
) {

    val visibleResults: List<SearchResultItem>
        get() = results
            .filter { selectedCategory == SearchCategory.ALL || it.category == selectedCategory }
            .filter { query.isBlank() || it.item.title.contains(query, ignoreCase = true) }

}
