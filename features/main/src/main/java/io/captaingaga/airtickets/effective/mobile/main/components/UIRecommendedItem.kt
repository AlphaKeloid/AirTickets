package io.captaingaga.airtickets.effective.mobile.main.components

data class UIRecommendedItem(
    val id: Int,
    val destinationTitle: String
) : DelegateAdapterItem {
    override fun id(): Any = this.id

    override fun content(): Any = Content(destinationTitle)

    inner class Content(
        val destinationTitle: String
    ) {
        override fun equals(other: Any?): Boolean {
            return if (other is Content) {
                destinationTitle == other.destinationTitle
            } else false
        }

        override fun hashCode(): Int = (37 * destinationTitle.hashCode())
    }
}
