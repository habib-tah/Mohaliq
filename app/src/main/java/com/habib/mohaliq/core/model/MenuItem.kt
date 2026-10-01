package com.habib.mohaliq.core.model

data class MenuItem(
    val id: String,
    val restaurantId: String,
    val name: String,
    val price: Double,
    val category: String
)
