package com.habib.mohaliq.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.habib.mohaliq.core.data.database.entity.PlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {

    @Query("SELECT * FROM places")
    fun observeAll(): Flow<List<PlaceEntity>>

    @Query("SELECT * FROM places WHERE type = :type")
    fun observeByType(type: String): Flow<List<PlaceEntity>>

    @Query("SELECT * FROM places WHERE id = :id")
    fun observeById(id: String): Flow<PlaceEntity?>

    @Query("SELECT * FROM places WHERE id = :id")
    suspend fun getById(id: String): PlaceEntity?

    @Query("SELECT * FROM places WHERE isFavorite = 1")
    fun observeFavorites(): Flow<List<PlaceEntity>>

    @Query("UPDATE places SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id: String, isFavorite: Boolean)

    @Query("SELECT COUNT(*) FROM places")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(places: List<PlaceEntity>)

    @Update
    suspend fun update(place: PlaceEntity)

}
