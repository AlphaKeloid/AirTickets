package io.captaingaga.airtickets.effective.mobile.domain.models

data class Offer(
    val id: Int,
    val title: String,
    val town: String,
    val price: Price
)
