package io.captaingaga.airtickets.effective.mobile.data.models

data class TicketModel(
    val id: Int,
    val badge: String,
    val price: String,
    val providerName: String,
    val company: String,
    val departure: FlightPointModel,
    val arrival: FlightPointModel,
    val flightTime: String,
    val hasTransfer: Boolean,
    val hasVisaTransfer: Boolean,
    val hasLuggage: Boolean,
    val luggagePrice: String,
    val hasHandLuggage: Boolean,
    val handLuggageSize: String,
    val isReturnable: Boolean,
    val isExchangeable: Boolean
)


