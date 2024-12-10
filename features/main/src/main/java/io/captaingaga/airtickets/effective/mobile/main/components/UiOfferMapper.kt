package io.captaingaga.airtickets.effective.mobile.main.components

import io.captaingaga.airtickets.effective.mobile.data.models.OfferModel

fun OfferModel.toUiItem() = UiOfferItem(
    id = this.id,
    artistName = this.title,
    city = this.town,
    price = this.price
)

fun List<OfferModel>.toUiItems() = map { it.toUiItem()}