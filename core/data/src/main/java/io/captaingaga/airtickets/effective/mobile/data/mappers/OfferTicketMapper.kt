package io.captaingaga.airtickets.effective.mobile.data.mappers

import io.captaingaga.airtickets.effective.mobile.data.models.OfferTicketModel
import io.captaingaga.airtickets.effective.mobile.data.utils.toRubles
import io.captaingaga.airtickets.effective.mobile.domain.models.DomainOfferTicket
import io.captaingaga.airtickets.effective.mobile.network.dto.OfferTicketDto

fun OfferTicketDto.toDomainOfferTicket() = DomainOfferTicket(
    id = this.id,
    title = this.title,
    timeRange = this.timeRange,
    price = this.price.toDomainPrice()
)

fun DomainOfferTicket.toOfferTicketModel() = OfferTicketModel(
    id = this.id,
    title = this.title,
    timeRange = this.timeRange,
    price = this.price.value.toRubles()
)

fun List<OfferTicketDto>.toDomainOfferTicketList() = map { it.toDomainOfferTicket() }

fun List<DomainOfferTicket>.toOfferTicketModelList() = map { it.toOfferTicketModel() }