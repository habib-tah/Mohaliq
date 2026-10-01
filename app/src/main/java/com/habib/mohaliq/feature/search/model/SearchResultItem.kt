package com.habib.mohaliq.feature.search.model

import com.habib.mohaliq.core.model.TravelCardItem

data class SearchResultItem(
    val item: TravelCardItem,
    val category: SearchCategory
)
