package io.captaingaga.airtickets.effective.mobile.search.comppnents

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RouteParam(
    val from: String = "",
    val to: String = ""
): Parcelable

fun RouteParam.asString() =
    StringBuilder().append(this.from).append(" - ").append(this.to).toString()
