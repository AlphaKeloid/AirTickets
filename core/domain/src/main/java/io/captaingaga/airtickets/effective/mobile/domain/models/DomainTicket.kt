package io.captaingaga.airtickets.effective.mobile.domain.models

data class DomainTicket(
    val id: Int,
    val badge: String,
    val price: DomainPrice,
    val providerName: String,
    val company: String,
    val departure: DomainFlightPoint,
    val arrival: DomainFlightPoint,
    val hasTransfer: Boolean,
    val hasVisaTransfer: Boolean,
    val luggage: DomainLuggage,
    val handLuggage: DomainHandLuggage,
    val isReturnable: Boolean,
    val isExchangeable: Boolean
)