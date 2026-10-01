package com.habib.mohaliq.feature.explore

sealed interface ExploreAction {

    data object ChangeLocationClicked : ExploreAction

    data object PopularSeeMoreClicked : ExploreAction

    data object FrequentlyVisitedSeeMoreClicked : ExploreAction

    data object NearestHotelSeeMoreClicked : ExploreAction

    data class PopularClicked(
        val itemId: String
    ) : ExploreAction

    data class FrequentlyVisitedClicked(
        val itemId: String
    ) : ExploreAction

    data class NearestHotelClicked(
        val itemId: String
    ) : ExploreAction

    data class FavoriteClicked(
        val itemId: String
    ) : ExploreAction

    data class BottomBarSelected(
        val index: Int
    ) : ExploreAction
}