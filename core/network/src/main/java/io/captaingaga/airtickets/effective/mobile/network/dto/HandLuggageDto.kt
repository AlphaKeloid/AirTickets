package io.captaingaga.airtickets.effective.mobile.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HandLuggageDto(
    @SerialName("has_hand_luggage")
    val hasHandLuggage: Boolean,
    val size: String? = null
)
