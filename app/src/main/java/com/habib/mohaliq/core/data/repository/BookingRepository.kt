package com.habib.mohaliq.core.data.repository

import com.habib.mohaliq.core.model.BookingRecord
import com.habib.mohaliq.core.model.BookingStatus
import com.habib.mohaliq.core.model.PlaceType
import kotlinx.coroutines.flow.Flow

interface BookingRepository {

    fun observeBookings(): Flow<List<BookingRecord>>

    suspend fun createBooking(
        placeId: String,
        placeType: PlaceType,
        title: String,
        location: String,
        dateRange: String,
        guestCount: Int,
        price: Double,
        status: BookingStatus = BookingStatus.UPCOMING
    ): Long

}
