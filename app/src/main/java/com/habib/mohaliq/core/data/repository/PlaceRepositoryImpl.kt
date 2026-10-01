package com.habib.mohaliq.core.data.repository

import com.habib.mohaliq.core.data.database.dao.MenuItemDao
import com.habib.mohaliq.core.data.database.dao.PlaceDao
import com.habib.mohaliq.core.data.database.dao.RoomOptionDao
import com.habib.mohaliq.core.data.database.entity.PlaceEntity
import com.habib.mohaliq.core.model.MenuItem
import com.habib.mohaliq.core.model.PlaceDetail
import com.habib.mohaliq.core.model.PlaceType
import com.habib.mohaliq.core.model.RoomOption
import com.habib.mohaliq.core.model.TravelCardItem
import com.habib.mohaliq.core.model.TypedPlace
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private fun PlaceEntity.toTravelCardItem() = TravelCardItem(
    id = id,
    title = title,
    location = location,
    rating = rating,
    reviewCount = reviewCount,
    currentPrice = currentPrice,
    originalPrice = originalPrice,
    badge = badge,
    isFavorite = isFavorite,
    imageUrl = imageUrl
)

private fun PlaceEntity.toPlaceDetail() = PlaceDetail(
    id = id,
    type = PlaceType.valueOf(type),
    title = title,
    location = location,
    description = description,
    rating = rating,
    reviewCount = reviewCount,
    currentPrice = currentPrice,
    originalPrice = originalPrice,
    badge = badge,
    imageUrl = imageUrl,
    latitude = latitude,
    longitude = longitude,
    isFavorite = isFavorite
)

private fun com.habib.mohaliq.core.data.database.entity.RoomOptionEntity.toRoomOption() = RoomOption(
    id = id,
    hotelId = hotelId,
    title = title,
    price = price,
    breakfastIncluded = breakfastIncluded,
    cancellationPolicy = cancellationPolicy,
    imageUrl = imageUrl
)

private fun com.habib.mohaliq.core.data.database.entity.MenuItemEntity.toMenuItem() = MenuItem(
    id = id,
    restaurantId = restaurantId,
    name = name,
    price = price,
    category = category
)

class PlaceRepositoryImpl @Inject constructor(
    private val placeDao: PlaceDao,
    private val roomOptionDao: RoomOptionDao,
    private val menuItemDao: MenuItemDao
) : PlaceRepository {

    override fun observeByType(type: PlaceType): Flow<List<TravelCardItem>> =
        placeDao.observeByType(type.name).map { list -> list.map { it.toTravelCardItem() } }

    override fun observeAll(): Flow<List<TypedPlace>> =
        placeDao.observeAll().map { list ->
            list.map { TypedPlace(it.toTravelCardItem(), PlaceType.valueOf(it.type)) }
        }

    override fun observeFavorites(): Flow<List<TypedPlace>> =
        placeDao.observeFavorites().map { list ->
            list.map { TypedPlace(it.toTravelCardItem(), PlaceType.valueOf(it.type)) }
        }

    override fun observeDetail(id: String): Flow<PlaceDetail?> =
        placeDao.observeById(id).map { it?.toPlaceDetail() }

    override suspend fun toggleFavorite(id: String) {
        val current = placeDao.getById(id) ?: return
        placeDao.setFavorite(id, !current.isFavorite)
    }

    override fun observeRoomOptions(hotelId: String): Flow<List<RoomOption>> =
        roomOptionDao.observeByHotelId(hotelId).map { list -> list.map { it.toRoomOption() } }

    override suspend fun getRoomOption(id: String): RoomOption? =
        roomOptionDao.getById(id)?.toRoomOption()

    override fun observeMenuItems(restaurantId: String): Flow<List<MenuItem>> =
        menuItemDao.observeByRestaurantId(restaurantId).map { list -> list.map { it.toMenuItem() } }

}
