package io.captaingaga.airtickets.effective.mobile.tickets.components

import io.captaingaga.airtickets.effective.mobile.data.models.TicketModel

fun TicketModel.toUiItem() = UITicketItem(
    id = this.id,
    badge = this.badge,
    price = this.price,
    departureTime = this.departure.time,
    departureAirport = this.departure.airport,
    arrivalTime = this.arrival.time,
    arrivalAirport = this.arrival.airport,
    flightTime = if (this.hasTransfer) "${this.flightTime} / Без пересадок" else this.flightTime
)

fun List<TicketModel>.toUiItems() = map { it.toUiItem() }