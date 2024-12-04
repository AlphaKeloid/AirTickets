package io.captaingaga.airtickets.effective.mobile.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class FlightPointDto(
    val town: String,
    val date: String,
    val airport: String
)