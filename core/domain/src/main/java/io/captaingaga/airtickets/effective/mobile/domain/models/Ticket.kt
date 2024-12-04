package io.captaingaga.airtickets.effective.mobile.domain.models

data class Ticket(
    val arrival: Arrival,
    val badge: String,
    val company: String,
    val departure: Departure,
    val handLuggage: HandLuggage,
    val hasTransfer: Boolean,
    val hasVisaTransfer: Boolean,
    val id: Int,
    val isExchangable: Boolean,
    val isReturnable: Boolean,
    val luggage: Luggage,
    val price: Price,
    val providerName: String
)