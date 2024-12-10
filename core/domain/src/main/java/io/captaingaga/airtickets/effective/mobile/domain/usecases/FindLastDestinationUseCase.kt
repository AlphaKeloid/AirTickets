package io.captaingaga.airtickets.effective.mobile.domain.usecases

import io.captaingaga.airtickets.effective.mobile.domain.models.DomainSearch
import io.captaingaga.airtickets.effective.mobile.domain.repositories.SearchRepository
import kotlinx.coroutines.flow.Flow

class FindLastDestinationUseCase(
    private val repository: SearchRepository,
) {
    operator fun invoke(): Flow<DomainSearch> = repository.findLast()
}