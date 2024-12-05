package io.captaingaga.airtickets.effective.mobile.data.mappers

import io.captaingaga.airtickets.effective.mobile.data.models.FlightPointModel
import io.captaingaga.airtickets.effective.mobile.data.models.TicketModel
import io.captaingaga.airtickets.effective.mobile.data.utils.FlightTimeUtil
import io.captaingaga.airtickets.effective.mobile.data.utils.toRubles
import io.captaingaga.airtickets.effective.mobile.domain.models.DomainFlightPoint
import io.captaingaga.airtickets.effective.mobile.domain.models.DomainHandLuggage
import io.captaingaga.airtickets.effective.mobile.domain.models.DomainLuggage
import io.captaingaga.airtickets.effective.mobile.domain.models.DomainPrice
import io.captaingaga.airtickets.effective.mobile.domain.models.DomainTicket
import io.captaingaga.airtickets.effective.mobile.network.dto.FlightPointDto
import io.captaingaga.airtickets.effective.mobile.network.dto.HandLuggageDto
import io.captaingaga.airtickets.effective.mobile.network.dto.LuggageDto
import io.captaingaga.airtickets.effective.mobile.network.dto.TicketDto

private fun FlightPointDto.toDomainFlightPoint() = DomainFlightPoint(
    town = this.town,
    date = this.date,
    airport = this.airport
)

private fun LuggageDto.toDomainLuggage() = DomainLuggage(
    hasLuggage = this.hasLuggage,
    price = this.price?.toDomainPrice() ?: DomainPrice(0)
)

private fun HandLuggageDto.toDomainHandLuggage() = DomainHandLuggage(
    hasHandLuggage = this.hasHandLuggage,
    size = this.size.orEmpty()
)

private fun DomainFlightPoint.toFlightPointModel() = FlightPointModel(
    town = this.town,
    date = this.date,
    airport = this.airport
)

fun TicketDto.toDomainTicket() = DomainTicket(
    id = this.id,
    badge = this.badge.orEmpty(),
    price = this.price.toDomainPrice(),
    providerName = this.providerName,
    company = this.company,
    departure = this.departure.toDomainFlightPoint(),
    arrival = this.arrival.toDomainFlightPoint(),
    hasTransfer = this.hasTransfer,
    hasVisaTransfer = this.hasVisaTransfer,
    luggage = this.luggage.toDomainLuggage(),
    handLuggage = this.handLuggage.toDomainHandLuggage(),
    isReturnable = this.isReturnable,
    isExchangeable = this.isExchangeable
)

fun DomainTicket.toTicketModel() = TicketModel(
    id = this.id,
    badge = this.badge,
    price = this.price.value.toRubles(),
    providerName = this.providerName,
    company = this.company,
    flightTime = FlightTimeUtil(
        departure = this.departure.date,
        arrival = this.arrival.date
    ),
    departure = this.departure.toFlightPointModel(),
    arrival = this.arrival.toFlightPointModel(),
    hasTransfer = this.hasTransfer,
    hasVisaTransfer = this.hasVisaTransfer,
    hasLuggage = this.luggage.hasLuggage,
    luggagePrice = this.luggage.price.value.takeIf { it != 0 }?.toRubles() ?: "",
    hasHandLuggage = this.handLuggage.hasHandLuggage,
    handLuggageSize = this.handLuggage.size,
    isReturnable = this.isReturnable,
    isExchangeable = this.isExchangeable
)

fun List<TicketDto>.toDomainList() = map { it.toDomainTicket() }

fun List<DomainTicket>.toTicketModelList() = map { it.toTicketModel() }