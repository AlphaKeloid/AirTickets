package io.captaingaga.airtickets.effective.mobile.search.comppnents.stub

import android.content.Context
import androidx.core.content.ContextCompat

internal fun Int.mapIconTint(context: Context): Int = when (this) {
    1 -> ContextCompat.getColor(
        context,
        io.captaingaga.airtickets.effective.mobile.common.R.color.red
    )

    10 -> ContextCompat.getColor(
        context,
        io.captaingaga.airtickets.effective.mobile.common.R.color.blue
    )

    30 -> ContextCompat.getColor(
        context,
        io.captaingaga.airtickets.effective.mobile.common.R.color.white
    )

    else -> throw IllegalArgumentException("Out of range")
}