package io.captaingaga.airtickets.effective.mobile.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Offers(
    val offers: List<OfferDto>
)
