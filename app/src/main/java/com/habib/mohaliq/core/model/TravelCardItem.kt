package com.habib.mohaliq.core.model

data class TravelCardItem(

    val id: String,

    val title: String,

    val location: String,

    val rating: Float,

    val reviewCount: Int,

    val currentPrice: Double,

    val originalPrice: Double? = null,

    val badge: String? = null,

    val isFavorite: Boolean = false,

    val imageUrl: String? = null

)