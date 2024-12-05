package io.captaingaga.airtickets.effective.mobile.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LuggageDto(
    @SerialName("has_luggage")
    val hasLuggage: Boolean,
    val price: PriceDto? = null
)
