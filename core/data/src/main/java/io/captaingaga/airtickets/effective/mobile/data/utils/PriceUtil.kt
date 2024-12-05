package io.captaingaga.airtickets.effective.mobile.data.utils

fun Int.toRubles(): String = "%,d \u20BD".format(this).replace(",", " ")