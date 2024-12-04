package io.captaingaga.airtickets.effective.mobile.domain.models

data class TicketOffer(
    val id: Int,
    val title: String,
    val timeRange: List<String>,
    val price: Price
)