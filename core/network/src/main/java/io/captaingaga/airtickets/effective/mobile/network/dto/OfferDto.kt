package io.captaingaga.airtickets.effective.mobile.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class OfferDto(
    val id: Int,
    val title: String,
    val town: String,
    val price: PriceDto
)
