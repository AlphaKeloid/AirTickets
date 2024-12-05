package io.captaingaga.airtickets.effective.mobile.data.di

import io.captaingaga.airtickets.effective.mobile.data.repositories.FlightRepositoryImpl
import io.captaingaga.airtickets.effective.mobile.domain.repositories.FlightRepository
import io.captaingaga.airtickets.effective.mobile.domain.usecases.FetchOffersTicketsUseCase
import io.captaingaga.airtickets.effective.mobile.domain.usecases.FetchOffersUseCase
import io.captaingaga.airtickets.effective.mobile.domain.usecases.FetchTicketsUseCase
import io.captaingaga.airtickets.effective.mobile.network.networkModule
import org.koin.dsl.module

val repositoriesModule = module {
    includes(networkModule)
    single<FlightRepository> { FlightRepositoryImpl(get()) }
    includes(useCasesModule)
}

val useCasesModule = module {
    single<FetchOffersUseCase> { FetchOffersUseCase(get()) }
    single<FetchOffersTicketsUseCase> { FetchOffersTicketsUseCase(get()) }
    single<FetchTicketsUseCase> { FetchTicketsUseCase(get()) }
}