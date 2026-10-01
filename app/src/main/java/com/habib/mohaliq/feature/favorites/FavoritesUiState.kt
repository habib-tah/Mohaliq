package com.habib.mohaliq.feature.favorites

import com.habib.mohaliq.feature.favorites.model.FavoriteCategory
import com.habib.mohaliq.feature.favorites.model.FavoriteItem

data class FavoritesUiState(
    val items: List<FavoriteItem> = emptyList(),
    val selectedCategory: FavoriteCategory = FavoriteCategory.ALL,
    val selectedBottomBarIndex: Int = 3
) {

    val visibleItems: List<FavoriteItem>
        get() = if (selectedCategory == FavoriteCategory.ALL)
            items
        else
            items.filter { it.category == selectedCategory }

}
