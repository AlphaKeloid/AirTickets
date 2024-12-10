package io.captaingaga.airtickets.effective.mobile.search.ui

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.captaingaga.airtickets.effective.mobile.search.comppnents.UITicketOfferItem
import io.captaingaga.airtickets.effective.mobile.search.comppnents.stub.mapIconTint
import io.captaingaga.airtickets.effective.mobile.search.databinding.ItemOfferTicketBinding

fun offersTicketsAdapter(
    onItemClick: (UITicketOfferItem) -> Unit
) = adapterDelegateViewBinding<UITicketOfferItem, UITicketOfferItem, ItemOfferTicketBinding>(
    viewBinding = { layoutInflater, parent ->
        ItemOfferTicketBinding.inflate(layoutInflater, parent, false)
    }
) {
    bind {
        binding.companyIcon
            .drawable
            .setTint(item.id.mapIconTint(context))
        binding.company.text = item.title
        binding.timeRange.text = item.timeRange
        binding.price.text = item.price
        binding.root.setOnClickListener { onItemClick(item) }
    }
}