package io.captaingaga.airtickets.effective.mobile.search.comppnents

data class RouteParam(
    val from: String = "",
    val to: String = ""
)

fun RouteParam.asString() =
    StringBuilder().append(this.from).append(" - ").append(this.to).toString()

fun RouteParam.isEmpty(): Boolean = (this.from.isEmpty() && this.to.isEmpty())