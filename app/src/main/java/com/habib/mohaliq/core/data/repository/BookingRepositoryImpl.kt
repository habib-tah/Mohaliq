package com.habib.mohaliq.core.data.repository

import com.habib.mohaliq.core.data.database.dao.BookingDao
import com.habib.mohaliq.core.data.database.entity.BookingEntity
import com.habib.mohaliq.core.model.BookingRecord
import com.habib.mohaliq.core.model.BookingStatus
import com.habib.mohaliq.core.model.PlaceType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private fun BookingEntity.toBookingRecord() = BookingRecord(
    id = id,
    placeId = placeId,
    placeType = PlaceType.valueOf(placeType),
    title = title,
    location = location,
    dateRange = dateRange,
    guestCount = guestCount,
    price = price,
    status = BookingStatus.valueOf(status)
)

class BookingRepositoryImpl @Inject constructor(
    private val bookingDao: BookingDao
) : BookingRepository {

    override fun observeBookings(): Flow<List<BookingRecord>> =
        bookingDao.observeAll().map { list -> list.map { it.toBookingRecord() } }

    override suspend fun createBooking(
        placeId: String,
        placeType: PlaceType,
        title: String,
        location: String,
        dateRange: String,
        guestCount: Int,
        price: Double,
        status: BookingStatus
    ): Long {
        return bookingDao.insert(
            BookingEntity(
                placeId = placeId,
                placeType = placeType.name,
                title = title,
                location = location,
                dateRange = dateRange,
                guestCount = guestCount,
                price = price,
                status = status.name,
                createdAt = System.currentTimeMillis()
            )
        )
    }

}
