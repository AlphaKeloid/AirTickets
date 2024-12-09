package io.captaingaga.airtickets.effective.mobile.data.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeSeparateUtil {

    operator fun invoke(dateTime: String): Pair<String, String> {
        val timeZone = TimeZone.currentSystemDefault()
        val local = LocalDateTime.parse(dateTime).toInstant(timeZone)

        val date = local.toEpochMilliseconds().toFormattedDate()
        val time = local.toLocalDateTime(timeZone).let {
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            formatter.format(it.toJavaLocalDateTime())
        }
        return Pair(date, time)
    }
}