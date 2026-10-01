package com.habib.mohaliq.feature.explore

import com.habib.mohaliq.core.model.PromotionBanner
import com.habib.mohaliq.core.model.TravelCardItem

data class ExploreUiState(

    val location: String = "Bali, Indonesia",

    val selectedBottomBar: Int = 2,

    val banner: PromotionBanner? = null,

    val popular: List<TravelCardItem> = emptyList(),

    val frequentlyVisited: List<TravelCardItem> = emptyList(),

    val nearestHotels: List<TravelCardItem> = emptyList()

)