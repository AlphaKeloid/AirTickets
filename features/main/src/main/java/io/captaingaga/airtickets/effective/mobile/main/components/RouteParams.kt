package io.captaingaga.airtickets.effective.mobile.main.components

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RouteParams(
    val departFrom: String = "",
    val arriveTo: String = "",
): Parcelable
