package io.captaingaga.airtickets.effective.mobile.search.comppnents

import io.captaingaga.airtickets.effective.mobile.data.models.OfferTicketModel

fun OfferTicketModel.toUiItem() = UITicketOfferItem(
    id = this.id,
    title = this.title,
    timeRange = this.timeRange.joinToString(separator = "  "),
    price = this.price
)

fun List<OfferTicketModel>.toUiItems() = map { it.toUiItem() }