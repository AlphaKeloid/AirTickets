package io.captaingaga.airtickets.effective.mobile.tickets.components

import io.captaingaga.airtickets.effective.mobile.common.DelegateAdapterItem

data class UITicketItem(
    val id: Int,
    val badge: String,
    val price: String,
    val departureTime: String,
    val departureAirport: String,
    val arrivalTime: String,
    val arrivalAirport: String,
    val flightTime: String
) : DelegateAdapterItem {
    override fun id(): Any = id

    override fun content(): Any = Content(
        badge, price, departureTime,
        departureAirport, arrivalTime,
        arrivalAirport, arrivalAirport
    )

    internal class Content(
        val badge: String,
        val price: String,
        val departureTime: String,
        val departureAirport: String,
        val arrivalTime: String,
        val arrivalAirport: String,
        val flightTime: String
    ) {
        override fun equals(other: Any?): Boolean {
            return if (other is Content) {
                badge == other.badge
                        && price == other.price
                        && departureTime == other.departureTime
                        && departureAirport == other.departureAirport
                        && arrivalTime == other.arrivalTime
                        && arrivalAirport == other.arrivalAirport
                        && flightTime == other.flightTime
            } else false
        }

        override fun hashCode(): Int {
            var result = badge.hashCode()
            result = 37 * result + price.hashCode()
            result = 37 * result + departureTime.hashCode()
            result = 37 * result + departureAirport.hashCode()
            result = 37 * result + arrivalTime.hashCode()
            result = 37 * result + arrivalAirport.hashCode()
            result = 37 * result + flightTime.hashCode()
            return result
        }
    }
}
