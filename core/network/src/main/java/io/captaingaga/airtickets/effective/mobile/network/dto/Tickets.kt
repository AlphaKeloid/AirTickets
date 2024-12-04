package io.captaingaga.airtickets.effective.mobile.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Tickets(
    val tickets: List<TicketDto>
)
