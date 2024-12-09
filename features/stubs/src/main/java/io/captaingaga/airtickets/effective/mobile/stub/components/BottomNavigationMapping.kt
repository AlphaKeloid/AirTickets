package io.captaingaga.airtickets.effective.mobile.stub.components

fun Int.mapBottomNavigationItems(): Pair<Int, Int> = when (this) {
    1 -> Pair(
        io.captaingaga.airtickets.effective.mobile.common.R.string.hotels,
        io.captaingaga.airtickets.effective.mobile.common.R.drawable.bed_24px

    )

    2 -> Pair(
        io.captaingaga.airtickets.effective.mobile.common.R.string.briefly,
        io.captaingaga.airtickets.effective.mobile.common.R.drawable.location_on_24px
    )

    3 -> Pair(
        io.captaingaga.airtickets.effective.mobile.common.R.string.subscriptions,
        io.captaingaga.airtickets.effective.mobile.common.R.drawable.notifications_24px
    )

    4 -> Pair(
        io.captaingaga.airtickets.effective.mobile.common.R.string.profile,
        io.captaingaga.airtickets.effective.mobile.common.R.drawable.person_24px
    )

    else -> throw IllegalArgumentException("Out of range")
}
