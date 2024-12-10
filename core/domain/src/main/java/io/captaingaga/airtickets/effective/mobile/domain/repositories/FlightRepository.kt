package io.captaingaga.airtickets.effective.mobile.domain.repositories

import io.captaingaga.airtickets.effective.mobile.domain.models.DomainOffer
import io.captaingaga.airtickets.effective.mobile.domain.models.DomainTicket
import io.captaingaga.airtickets.effective.mobile.domain.models.DomainOfferTicket
import kotlinx.coroutines.flow.Flow

interface FlightRepository {

    suspend fun fetchOffers(): Flow<List<DomainOffer>>

    suspend fun fetchOffersTickets(): Flow<List<DomainOfferTicket>>

    suspend fun fetchTickets(): Flow<List<DomainTicket>>
}