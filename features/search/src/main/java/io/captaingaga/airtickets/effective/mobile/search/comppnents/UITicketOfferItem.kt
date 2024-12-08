package io.captaingaga.airtickets.effective.mobile.search.comppnents

import io.captaingaga.airtickets.effective.mobile.common.DelegateAdapterItem

data class UITicketOfferItem(
    val id: Int,
    val title: String,
    val timeRange: String,
    val price: String
) : DelegateAdapterItem {
    override fun id(): Any = id

    override fun content(): Any = Content(title, timeRange, price)

    inner class Content(val title: String, val timeRange: String, val price: String) {
        override fun equals(other: Any?): Boolean {
            return if (other is Content) {
                title == other.title && timeRange == other.timeRange && price == other.price
            } else false
        }

        override fun hashCode(): Int {
            var result = title.hashCode()
            result = 37 * result + timeRange.hashCode()
            result = 37 * result + price.hashCode()
            return result
        }
    }
}
