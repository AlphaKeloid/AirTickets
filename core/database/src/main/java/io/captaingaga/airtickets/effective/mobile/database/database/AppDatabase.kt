package io.captaingaga.airtickets.effective.mobile.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.captaingaga.airtickets.effective.mobile.database.dao.SearchDao
import io.captaingaga.airtickets.effective.mobile.database.entity.SearchEntity

@Database(entities = [(SearchEntity::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao
}

fun databaseBuilder(appContext: Context): AppDatabase = Room.databaseBuilder(
    context = appContext,
    klass = AppDatabase::class.java,
    name = "app_database"
).build()

fun daoBuilder(appDatabase: AppDatabase): SearchDao = appDatabase.searchDao()