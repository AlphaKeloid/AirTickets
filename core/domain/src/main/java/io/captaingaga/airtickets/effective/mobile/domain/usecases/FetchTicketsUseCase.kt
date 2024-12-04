package io.captaingaga.airtickets.effective.mobile.domain.usecases

import io.captaingaga.airtickets.effective.mobile.domain.models.Ticket
import io.captaingaga.airtickets.effective.mobile.domain.repositories.FlightRepository
import kotlinx.coroutines.flow.Flow

class FetchTicketsUseCase(
    private val repository: FlightRepository
) {

    suspend operator fun invoke(): Flow<List<Ticket>> = repository.fetchTickets()
}