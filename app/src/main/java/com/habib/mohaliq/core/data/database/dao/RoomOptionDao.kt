package com.habib.mohaliq.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.habib.mohaliq.core.data.database.entity.RoomOptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomOptionDao {

    @Query("SELECT * FROM room_options WHERE hotelId = :hotelId")
    fun observeByHotelId(hotelId: String): Flow<List<RoomOptionEntity>>

    @Query("SELECT * FROM room_options WHERE id = :id")
    suspend fun getById(id: String): RoomOptionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rooms: List<RoomOptionEntity>)

    @Query("SELECT COUNT(*) FROM room_options")
    suspend fun count(): Int

}
