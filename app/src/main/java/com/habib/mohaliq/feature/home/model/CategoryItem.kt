package com.habib.mohaliq.feature.home.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CategoryItem(
    val id: String,
    @DrawableRes val iconRes: Int,
    @StringRes val labelRes: Int
)