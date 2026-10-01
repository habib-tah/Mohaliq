package com.habib.mohaliq.core.data.repository

import com.habib.mohaliq.core.model.MenuItem
import com.habib.mohaliq.core.model.PlaceDetail
import com.habib.mohaliq.core.model.PlaceType
import com.habib.mohaliq.core.model.RoomOption
import com.habib.mohaliq.core.model.TravelCardItem
import com.habib.mohaliq.core.model.TypedPlace
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {

    fun observeByType(type: PlaceType): Flow<List<TravelCardItem>>

    fun observeAll(): Flow<List<TypedPlace>>

    fun observeFavorites(): Flow<List<TypedPlace>>

    fun observeDetail(id: String): Flow<PlaceDetail?>

    suspend fun toggleFavorite(id: String)

    fun observeRoomOptions(hotelId: String): Flow<List<RoomOption>>

    suspend fun getRoomOption(id: String): RoomOption?

    fun observeMenuItems(restaurantId: String): Flow<List<MenuItem>>

}
