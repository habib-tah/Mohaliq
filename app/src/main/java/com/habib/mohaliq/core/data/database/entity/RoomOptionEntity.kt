package com.habib.mohaliq.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_options")
data class RoomOptionEntity(

    @PrimaryKey val id: String,

    val hotelId: String,

    val title: String,

    val price: Double,

    val breakfastIncluded: Boolean = false,

    val cancellationPolicy: String,

    val imageUrl: String

)
