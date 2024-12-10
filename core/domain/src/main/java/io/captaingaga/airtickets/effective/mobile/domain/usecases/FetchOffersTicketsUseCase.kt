package io.captaingaga.airtickets.effective.mobile.domain.usecases

import io.captaingaga.airtickets.effective.mobile.domain.models.DomainOfferTicket
import io.captaingaga.airtickets.effective.mobile.domain.repositories.FlightRepository
import kotlinx.coroutines.flow.Flow

class FetchOffersTicketsUseCase(
    private val repository: FlightRepository
) {
    suspend operator fun invoke(): Flow<List<DomainOfferTicket>> = repository.fetchOffersTickets()
}