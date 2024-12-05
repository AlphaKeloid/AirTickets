package io.captaingaga.airtickets.effective.mobile.data.mappers

import io.captaingaga.airtickets.effective.mobile.domain.models.DomainPrice
import io.captaingaga.airtickets.effective.mobile.network.dto.PriceDto

fun PriceDto.toDomainPrice() = DomainPrice(
    value = this.value
)
