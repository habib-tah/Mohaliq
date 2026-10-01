package com.habib.mohaliq.feature.home

import com.habib.mohaliq.core.model.TravelCardItem
import com.habib.mohaliq.feature.home.model.CategoryItem

sealed interface HomeAction {

    data class SearchQueryChanged(
        val query: String
    ) : HomeAction

    data class CategoryClicked(
        val category: CategoryItem
    ) : HomeAction

    data class PopularClicked(
        val item: TravelCardItem
    ) : HomeAction

    data class RecommendationClicked(
        val item: TravelCardItem
    ) : HomeAction

    data class FavoriteClicked(
        val itemId: String
    ) : HomeAction

    data class BottomBarItemSelected(
        val index: Int
    ) : HomeAction

    data object PopularSeeMoreClicked : HomeAction

    data object RecommendationSeeMoreClicked : HomeAction

    data object SearchBarClicked : HomeAction

    data object AiAssistantClicked : HomeAction
}
