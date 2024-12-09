package io.captaingaga.airtickets.effective.mobile.data.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Long.toFormattedDate(): String {
    val localDate = Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
    val formatter = DateTimeFormatter.ofPattern("d MMM, E", Locale("ru"))
    return localDate.toJavaLocalDate().format(formatter)
}