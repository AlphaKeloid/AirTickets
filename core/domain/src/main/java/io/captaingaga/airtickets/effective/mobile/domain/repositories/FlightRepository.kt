package io.captaingaga.airtickets.effective.mobile.domain.repositories

import io.captaingaga.airtickets.effective.mobile.domain.models.Offer
import io.captaingaga.airtickets.effective.mobile.domain.models.Ticket
import io.captaingaga.airtickets.effective.mobile.domain.models.TicketOffer
import kotlinx.coroutines.flow.Flow

interface FlightRepository {

    suspend fun fetchOffers(): Flow<List<TicketOffer>>

    suspend fun fetchTicketOffers(): Flow<List<Offer>>

    suspend fun fetchTickets(): Flow<List<Ticket>>
}