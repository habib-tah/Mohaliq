package com.habib.mohaliq.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Unifies hotels, restaurants, and destinations in one table — they all
 * share the same "card" shape (title, location, rating, price, image).
 * [type] discriminates which kind of place a row represents.
 */
@Entity(tableName = "places")
data class PlaceEntity(

    @PrimaryKey val id: String,

    val type: String, // PlaceType.name — HOTEL, RESTAURANT, DESTINATION

    val title: String,

    val location: String,

    val description: String,

    val rating: Float,

    val reviewCount: Int,

    val currentPrice: Double,

    val originalPrice: Double? = null,

    val badge: String? = null,

    val imageUrl: String,

    val latitude: Double,

    val longitude: Double,

    val isFavorite: Boolean = false

)
