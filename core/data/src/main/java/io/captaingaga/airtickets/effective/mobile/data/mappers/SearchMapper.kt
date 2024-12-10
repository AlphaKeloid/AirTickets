package io.captaingaga.airtickets.effective.mobile.data.mappers

import io.captaingaga.airtickets.effective.mobile.data.models.SearchModel
import io.captaingaga.airtickets.effective.mobile.database.entity.SearchEntity
import io.captaingaga.airtickets.effective.mobile.domain.models.DomainSearch

fun SearchEntity.toDomainSearch() = DomainSearch(
    id = this.id,
    destination = this.destination
)

fun DomainSearch.toSearchModel() = SearchModel(
    id = this.id,
    destination = this.destination
)

fun DomainSearch.toSearchEntity() = SearchEntity(
    id = this.id,
    destination = this.destination
)

