    package com.habib.mohaliq.feature.home

    import com.habib.mohaliq.core.model.TravelCardItem
    import com.habib.mohaliq.feature.home.model.CategoryItem

    data class HomeUiState(

        val searchQuery: String = "",

        val categories: List<CategoryItem> = emptyList(),

        val popularItems: List<TravelCardItem> = emptyList(),

        val recommendationItems: List<TravelCardItem> = emptyList(),

        val selectedBottomBarIndex: Int = 0
    )
