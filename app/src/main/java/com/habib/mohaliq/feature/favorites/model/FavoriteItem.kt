package com.habib.mohaliq.feature.favorites.model

import com.habib.mohaliq.core.model.TravelCardItem

data class FavoriteItem(
    val item: TravelCardItem,
    val category: FavoriteCategory
)
