package com.habib.mohaliq.feature.hotel.model

import androidx.annotation.DrawableRes

data class FacilityItem(
    val id: String,
    @DrawableRes val icon: Int,
    val title: String
)