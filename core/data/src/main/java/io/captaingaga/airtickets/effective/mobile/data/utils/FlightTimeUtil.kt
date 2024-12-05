package io.captaingaga.airtickets.effective.mobile.data.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

object FlightTimeUtil {

    operator fun invoke(departure: String, arrival: String): String {
        val timeZone = TimeZone.currentSystemDefault()
        val departureInstant = LocalDateTime.parse(departure).toInstant(timeZone)
        val arrivalInstant = LocalDateTime.parse(arrival).toInstant(timeZone)
        val duration = arrivalInstant - departureInstant
        return StringBuilder()
            .append(duration.inWholeHours)
            .append(".")
            .append(duration.inWholeMinutes % 60)
            .append(" ч в пути")
            .toString()
    }
}