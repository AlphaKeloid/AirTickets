package io.captaingaga.airtickets.effective.mobile.domain.usecases

import io.captaingaga.airtickets.effective.mobile.domain.models.Offer
import io.captaingaga.airtickets.effective.mobile.domain.repositories.FlightRepository
import kotlinx.coroutines.flow.Flow

class FetchOffersUseCase(
    private val repository: FlightRepository
) {
    suspend operator fun invoke(): Flow<List<Offer>> = repository.fetchTicketOffers()
}