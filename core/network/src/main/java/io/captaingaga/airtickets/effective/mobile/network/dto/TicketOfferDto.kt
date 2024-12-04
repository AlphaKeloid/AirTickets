package io.captaingaga.airtickets.effective.mobile.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TicketOfferDto(
    val id: Int,
    val title: String,
    @SerialName("time_range")
    val timeRange: List<String>,
    val price: PriceDto
)