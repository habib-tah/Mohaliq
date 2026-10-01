package com.habib.mohaliq.app.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.habib.mohaliq.core.data.database.MohaliqDatabase
import com.habib.mohaliq.core.data.database.dao.BookingDao
import com.habib.mohaliq.core.data.database.dao.MenuItemDao
import com.habib.mohaliq.core.data.database.dao.PlaceDao
import com.habib.mohaliq.core.data.database.dao.RoomOptionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        placeDaoProvider: Provider<PlaceDao>,
        roomOptionDaoProvider: Provider<RoomOptionDao>,
        menuItemDaoProvider: Provider<MenuItemDao>,
        @ApplicationScope applicationScope: CoroutineScope
    ): MohaliqDatabase {

        val migration1To2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    ALTER TABLE places
                    ADD COLUMN latitude REAL NOT NULL DEFAULT 0.0
                    """.trimIndent()
                )

                db.execSQL(
                    """
                    ALTER TABLE places
                    ADD COLUMN longitude REAL NOT NULL DEFAULT 0.0
                    """.trimIndent()
                )
            }
        }

        return Room.databaseBuilder(
            context,
            MohaliqDatabase::class.java,
            "mohaliq.db"
        )
            .addMigrations(migration1To2)
            .addCallback(
                MohaliqDatabase.SeedCallback(
                    placeDaoProvider,
                    roomOptionDaoProvider,
                    menuItemDaoProvider,
                    applicationScope
                )
            )
            .build()
    }

    @Provides
    fun providePlaceDao(database: MohaliqDatabase): PlaceDao =
        database.placeDao()

    @Provides
    fun provideRoomOptionDao(database: MohaliqDatabase): RoomOptionDao =
        database.roomOptionDao()

    @Provides
    fun provideMenuItemDao(database: MohaliqDatabase): MenuItemDao =
        database.menuItemDao()

    @Provides
    fun provideBookingDao(database: MohaliqDatabase): BookingDao =
        database.bookingDao()
}