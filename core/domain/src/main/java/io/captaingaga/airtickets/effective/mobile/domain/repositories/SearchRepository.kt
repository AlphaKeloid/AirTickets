package io.captaingaga.airtickets.effective.mobile.domain.repositories

import io.captaingaga.airtickets.effective.mobile.domain.models.DomainSearch
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun insert(domain: DomainSearch)

    fun findLast(): Flow<DomainSearch>
}