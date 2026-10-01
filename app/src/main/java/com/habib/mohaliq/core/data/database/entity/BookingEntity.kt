package com.habib.mohaliq.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class BookingEntity(

    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    val placeId: String,

    val placeType: String, // PlaceType.name

    val title: String,

    val location: String,

    val dateRange: String,

    val guestCount: Int,

    val price: Double,

    val status: String, // BookingStatus.name

    val createdAt: Long

)
