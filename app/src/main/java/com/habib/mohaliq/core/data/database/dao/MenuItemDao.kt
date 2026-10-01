package com.habib.mohaliq.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.habib.mohaliq.core.data.database.entity.MenuItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuItemDao {

    @Query("SELECT * FROM menu_items WHERE restaurantId = :restaurantId")
    fun observeByRestaurantId(restaurantId: String): Flow<List<MenuItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MenuItemEntity>)

    @Query("SELECT COUNT(*) FROM menu_items")
    suspend fun count(): Int

}
