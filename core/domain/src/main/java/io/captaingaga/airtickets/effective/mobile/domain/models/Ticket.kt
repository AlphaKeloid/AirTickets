package io.captaingaga.airtickets.effective.mobile.domain.models

data class Ticket(
    val id: Int,
    val badge: String,
    val price: Price,
    val providerName: String,
    val company: String,
    val departure: FlightPoint,
    val arrival: FlightPoint,
    val hasTransfer: Boolean,
    val hasVisaTransfer: Boolean,
    val luggage: Luggage,
    val handLuggage: HandLuggage,
    val isReturnable: Boolean,
    val isExchangeable: Boolean
)