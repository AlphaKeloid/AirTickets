package io.captaingaga.airtickets.effective.mobile.main.components

data class UiOfferItem(
    val id: Int,
    val artistName: String,
    val city: String,
    val price: String
) : DelegateAdapterItem {

    override fun id(): Any = id

    override fun content(): Any = Content(artistName, city, price)

    inner class Content(
        val artistName: String,
        val city: String,
        val price: String
    ) {
        override fun equals(other: Any?): Boolean {
            return if (other is Content) {
                artistName == other.artistName && city == other.city && price == other.price
            } else false
        }

        override fun hashCode(): Int {
            var result = artistName.hashCode()
            result = 37 * result + city.hashCode()
            result = 37 * result + price.hashCode()
            return result
        }
    }
}
