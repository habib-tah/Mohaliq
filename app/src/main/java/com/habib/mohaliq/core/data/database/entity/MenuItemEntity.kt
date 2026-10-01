package com.habib.mohaliq.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_items")
data class MenuItemEntity(

    @PrimaryKey val id: String,

    val restaurantId: String,

    val name: String,

    val price: Double,

    val category: String

)
