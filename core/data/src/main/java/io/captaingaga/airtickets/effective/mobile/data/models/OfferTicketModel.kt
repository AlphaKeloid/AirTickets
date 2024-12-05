package io.captaingaga.airtickets.effective.mobile.data.models

data class OfferTicketModel(
    val id: Int,
    val title: String,
    val timeRange: List<String>,
    val price: String
)
