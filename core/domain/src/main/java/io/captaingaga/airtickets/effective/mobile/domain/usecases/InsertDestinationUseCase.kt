package io.captaingaga.airtickets.effective.mobile.domain.usecases

import io.captaingaga.airtickets.effective.mobile.domain.models.DomainSearch
import io.captaingaga.airtickets.effective.mobile.domain.repositories.SearchRepository

class InsertDestinationUseCase(
    private val repository: SearchRepository,
) {
    suspend operator fun invoke(domain: DomainSearch) = repository.insert(domain)
}