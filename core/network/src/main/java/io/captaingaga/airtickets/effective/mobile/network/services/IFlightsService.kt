package io.captaingaga.airtickets.effective.mobile.network.services

import io.captaingaga.airtickets.effective.mobile.network.dto.Offers
import io.captaingaga.airtickets.effective.mobile.network.dto.Tickets
import io.captaingaga.airtickets.effective.mobile.network.dto.TicketsOffers
import retrofit2.Response
import retrofit2.http.GET


interface IFlightsService {

    @GET("offers")
    suspend fun fetchOffers(): Response<Offers>

    @GET("tickets")
    suspend fun fetchTickets(): Response<Tickets>

    @GET("tickets_offers")
    suspend fun fetchTicketsOffers(): Response<TicketsOffers>
}