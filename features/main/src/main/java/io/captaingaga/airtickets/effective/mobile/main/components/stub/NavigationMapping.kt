package io.captaingaga.airtickets.effective.mobile.main.components.stub

fun Int.mapNavigationItem(): Pair<Int, Int> = when (this) {
    1 -> Pair(
        io.captaingaga.airtickets.effective.mobile.common.R.string.complex_route_text_stub,
        io.captaingaga.airtickets.effective.mobile.common.R.drawable.keyboard_command_key_24px

    )

    2 -> Pair(
        io.captaingaga.airtickets.effective.mobile.common.R.string.weekend_text_stab,
        io.captaingaga.airtickets.effective.mobile.common.R.drawable.calendar_month_24px
    )

    3 -> Pair(
        io.captaingaga.airtickets.effective.mobile.common.R.string.last_minute_tickets_text_stub,
        io.captaingaga.airtickets.effective.mobile.common.R.drawable.local_fire_department_24px
    )

    else -> throw IllegalArgumentException("Out of range")
}
