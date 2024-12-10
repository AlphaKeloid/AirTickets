package io.captaingaga.airtickets.effective.mobile.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class FlightsApiClient(
    private val httpClient: HttpClient
) {

    operator fun invoke(): Retrofit = Retrofit.Builder()
        .baseUrl("https://google.com")
        .client(httpClient())
        .addConverterFactory(
            Json.asConverterFactory(
                MediaType.get("application/json; charset=UTF8")
            )
        )
        .build()
}