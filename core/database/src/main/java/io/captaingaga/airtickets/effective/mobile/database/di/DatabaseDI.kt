package io.captaingaga.airtickets.effective.mobile.database.di

import io.captaingaga.airtickets.effective.mobile.database.dao.SearchDao
import io.captaingaga.airtickets.effective.mobile.database.database.AppDatabase
import io.captaingaga.airtickets.effective.mobile.database.database.daoBuilder
import io.captaingaga.airtickets.effective.mobile.database.database.databaseBuilder
import org.koin.dsl.module

val databaseModule = module {
    single<AppDatabase> { databaseBuilder(get()) }
    single<SearchDao> { daoBuilder(get()) }
}