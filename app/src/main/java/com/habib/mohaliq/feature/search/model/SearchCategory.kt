package com.habib.mohaliq.feature.search.model

import androidx.annotation.StringRes
import com.habib.mohaliq.R

enum class SearchCategory(@StringRes val labelRes: Int) {
    ALL(R.string.filter_all),
    HOTEL(R.string.category_hotel),
    RESTAURANT(R.string.category_restaurant),
    DESTINATION(R.string.category_destination)
}
