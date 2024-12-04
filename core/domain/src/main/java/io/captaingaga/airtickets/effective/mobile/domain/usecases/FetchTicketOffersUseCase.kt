package io.captaingaga.airtickets.effective.mobile.domain.usecases

import io.captaingaga.airtickets.effective.mobile.domain.models.TicketOffer
import io.captaingaga.airtickets.effective.mobile.domain.repositories.FlightRepository
import kotlinx.coroutines.flow.Flow

class FetchTicketOffersUseCase(
    private val repository: FlightRepository
) {
    suspend operator fun invoke(): Flow<List<TicketOffer>> = repository.fetchTicketOffers()
}