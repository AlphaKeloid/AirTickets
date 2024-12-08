package io.captaingaga.airtickets.effective.mobile.search.comppnents

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

internal fun Long.toFormattedDate(): String {
    val localDate = Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
    val formatter = DateTimeFormatter.ofPattern("d MMM, E", Locale("ru"))
    return localDate.toJavaLocalDate().format(formatter)
}