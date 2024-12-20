package io.captaingaga.airtickets.effective.mobile.domain.usecases

import io.captaingaga.airtickets.effective.mobile.domain.models.DomainOffer
import io.captaingaga.airtickets.effective.mobile.domain.repositories.FlightRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class FetchOffersUseCase(
    private val repository: FlightRepository
) {
    suspend operator fun invoke(): Flow<List<DomainOffer>> = repository.fetchOffers()
}