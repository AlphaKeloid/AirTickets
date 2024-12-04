package io.captaingaga.airtickets.effective.mobile.network

import io.captaingaga.airtickets.effective.mobile.network.services.IFlightsService
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single<HttpClient> { HttpClient() }
    single<Retrofit> { FlightsApiClient(get()).invoke() }
    single<IFlightsService> { get<Retrofit>().create(IFlightsService::class.java) }
}