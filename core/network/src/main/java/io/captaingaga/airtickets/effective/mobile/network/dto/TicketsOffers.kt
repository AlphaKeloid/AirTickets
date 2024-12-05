package io.captaingaga.airtickets.effective.mobile.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TicketsOffers(
    @SerialName("tickets_offers")
    val ticketsOffers: List<OfferTicketDto>
)
