package com.habib.mohaliq.feature.favorites

import com.habib.mohaliq.feature.favorites.model.FavoriteCategory

sealed interface FavoritesAction {

    data class CategorySelected(val category: FavoriteCategory) : FavoritesAction

    data class ItemClicked(val itemId: String) : FavoritesAction

    data class FavoriteRemoved(val itemId: String) : FavoritesAction

    data class BottomBarItemSelected(val index: Int) : FavoritesAction

}
