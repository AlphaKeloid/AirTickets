package io.captaingaga.airtickets.effective.mobile.data.repositories

import io.captaingaga.airtickets.effective.mobile.data.mappers.toDomainList
import io.captaingaga.airtickets.effective.mobile.data.mappers.toDomainOdderList
import io.captaingaga.airtickets.effective.mobile.data.mappers.toDomainOfferTicketList
import io.captaingaga.airtickets.effective.mobile.domain.models.DomainOffer
import io.captaingaga.airtickets.effective.mobile.domain.models.DomainOfferTicket
import io.captaingaga.airtickets.effective.mobile.domain.models.DomainTicket
import io.captaingaga.airtickets.effective.mobile.domain.repositories.FlightRepository
import io.captaingaga.airtickets.effective.mobile.network.services.IFlightsService
import io.captaingaga.airtickets.effective.mobile.network.wrapAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FlightRepositoryImpl(
    private val service: IFlightsService
) : FlightRepository {
    override suspend fun fetchOffers(): Flow<List<DomainOffer>> {
        return service.fetchOffers()
            .wrapAsFlow()
            .map {
                it.offers.toDomainOdderList()
            }
    }

    override suspend fun fetchOffersTickets(): Flow<List<DomainOfferTicket>> {
        return service.fetchTicketsOffers()
            .wrapAsFlow()
            .map {
                it.ticketsOffers.toDomainOfferTicketList()
            }
    }

    override suspend fun fetchTickets(): Flow<List<DomainTicket>> {
        return service.fetchTickets()
            .wrapAsFlow()
            .map {
                it.tickets.toDomainList()
            }
    }
}