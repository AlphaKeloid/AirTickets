package io.captaingaga.airtickets.effective.mobile.data.repositories

import io.captaingaga.airtickets.effective.mobile.data.mappers.toDomainSearch
import io.captaingaga.airtickets.effective.mobile.data.mappers.toSearchEntity
import io.captaingaga.airtickets.effective.mobile.database.dao.SearchDao
import io.captaingaga.airtickets.effective.mobile.domain.models.DomainSearch
import io.captaingaga.airtickets.effective.mobile.domain.repositories.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class SearchRepositoryImpl(
    private val dao: SearchDao,
) : SearchRepository {

    override suspend fun insert(domain: DomainSearch) = dao.insert(domain.toSearchEntity())

    override fun findLast(): Flow<DomainSearch> = dao.findLast().mapNotNull { it?.toDomainSearch() }
}