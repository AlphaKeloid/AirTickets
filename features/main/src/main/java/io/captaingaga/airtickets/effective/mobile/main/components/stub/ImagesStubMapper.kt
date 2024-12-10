package io.captaingaga.airtickets.effective.mobile.main.components.stub

internal fun Int.mapOfferImageFromAssets(): String = when (this) {
    1 -> "file:///android_asset/offers/die_antwoord.jpg"
    2 -> "file:///android_asset/offers/socrat_and_lera.jpeg"
    3 -> "file:///android_asset/offers/lampabict.jpg"
    else -> throw IllegalArgumentException("Out of range")
}

internal fun Int.mapRecommendedImageFromAssets(): String = when (this) {
    1 -> "file:///android_asset/recommended/stambul.jpeg"
    2 -> "file:///android_asset/recommended/sochi.jpeg"
    3 -> "file:///android_asset/recommended/phuket.jpeg"
    else -> throw IllegalArgumentException("Out of range")
}