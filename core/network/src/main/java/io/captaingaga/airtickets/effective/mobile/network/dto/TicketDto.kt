package io.captaingaga.airtickets.effective.mobile.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TicketDto(
    val id: Int,
    val badge: String? = null,
    val price: PriceDto,
    @SerialName("provider_name")
    val providerName: String,
    val company: String,
    val departure: FlightPointDto,
    val arrival: FlightPointDto,
    @SerialName("has_transfer")
    val hasTransfer: Boolean,
    @SerialName("has_visa_transfer")
    val hasVisaTransfer: Boolean,
    val luggage: LuggageDto,
    @SerialName("hand_luggage")
    val handLuggage: HandLuggageDto,
    @SerialName("is_returnable")
    val isReturnable: Boolean,
    @SerialName("is_exchangeable")
    val isExchangeable: Boolean
)
