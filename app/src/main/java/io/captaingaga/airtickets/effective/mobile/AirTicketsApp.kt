package io.captaingaga.airtickets.effective.mobile

import android.app.Application
import io.captaingaga.airtickets.effective.mobile.data.di.repositoriesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class AirTicketsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val dataModule = module {
            repositoriesModule.includes()
        }
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AirTicketsApp)
            modules(
                dataModule
            )
        }
    }
}