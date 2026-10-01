package com.habib.mohaliq.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.habib.mohaliq.core.data.database.dao.BookingDao
import com.habib.mohaliq.core.data.database.dao.MenuItemDao
import com.habib.mohaliq.core.data.database.dao.PlaceDao
import com.habib.mohaliq.core.data.database.dao.RoomOptionDao
import com.habib.mohaliq.core.data.database.entity.BookingEntity
import com.habib.mohaliq.core.data.database.entity.MenuItemEntity
import com.habib.mohaliq.core.data.database.entity.PlaceEntity
import com.habib.mohaliq.core.data.database.entity.RoomOptionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider

@Database(
    entities = [
        PlaceEntity::class,
        RoomOptionEntity::class,
        MenuItemEntity::class,
        BookingEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class MohaliqDatabase : RoomDatabase() {

    abstract fun placeDao(): PlaceDao

    abstract fun roomOptionDao(): RoomOptionDao

    abstract fun menuItemDao(): MenuItemDao

    abstract fun bookingDao(): BookingDao

    /**
     * Seeds the database with curated content the first time the DB file
     * is created. Runs once — after this, all content is just whatever's
     * in the (persisted) database, including any favorites/bookings the
     * user creates.
     */
    class SeedCallback(
        private val placeDaoProvider: Provider<PlaceDao>,
        private val roomOptionDaoProvider: Provider<RoomOptionDao>,
        private val menuItemDaoProvider: Provider<MenuItemDao>,
        private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            applicationScope.launch(Dispatchers.IO) {
                placeDaoProvider.get().insertAll(SeedData.places)
                roomOptionDaoProvider.get().insertAll(SeedData.roomOptions)
                menuItemDaoProvider.get().insertAll(SeedData.menuItems)
            }

        }

    }

}
