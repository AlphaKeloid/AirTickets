package io.captaingaga.airtickets.effective.mobile

import android.app.Application
import io.captaingaga.airtickets.effective.mobile.data.di.repositoriesModule
import io.captaingaga.airtickets.effective.mobile.data.di.useCasesModule
import io.captaingaga.airtickets.effective.mobile.database.di.databaseModule
import io.captaingaga.airtickets.effective.mobile.main.di.featureMainModule
import io.captaingaga.airtickets.effective.mobile.network.networkModule
import io.captaingaga.airtickets.effective.mobile.search.di.featureSearchModule
import io.captaingaga.airtickets.effective.mobile.tickets.di.ticketsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AirTicketsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AirTicketsApp)
            modules(
                databaseModule,
                networkModule,
                repositoriesModule,
                useCasesModule,
                featureMainModule,
                featureSearchModule,
                ticketsModule
            )
        }
    }
}