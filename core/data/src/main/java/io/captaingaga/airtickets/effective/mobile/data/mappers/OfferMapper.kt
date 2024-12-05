package io.captaingaga.airtickets.effective.mobile.data.mappers

import io.captaingaga.airtickets.effective.mobile.data.models.OfferModel
import io.captaingaga.airtickets.effective.mobile.data.utils.toRubles
import io.captaingaga.airtickets.effective.mobile.domain.models.DomainOffer
import io.captaingaga.airtickets.effective.mobile.network.dto.OfferDto

fun OfferDto.toDomainOffer() = DomainOffer(
    id = this.id,
    title = this.title,
    town = this.town,
    price = this.price.toDomainPrice()
)

fun DomainOffer.toOfferModel() = OfferModel(
    id = this.id,
    title = this.title,
    town = this.town,
    price = StringBuilder()
        .append("от ")
        .append(price.value.toRubles())
        .toString()
)

fun List<OfferDto>.toDomainOdderList() = map { it.toDomainOffer() }

fun List<DomainOffer>.toOfferModelList() = map { it.toOfferModel() }