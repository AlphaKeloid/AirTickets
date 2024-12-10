package io.captaingaga.airtickets.effective.mobile.tickets.ui

import android.view.View
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.captaingaga.airtickets.effective.mobile.tickets.components.UITicketItem
import io.captaingaga.airtickets.effective.mobile.tickets.databinding.ItemTicketBinding

fun ticketsAdapter(
    onItemClick: (UITicketItem) -> Unit
) = adapterDelegateViewBinding<UITicketItem, UITicketItem, ItemTicketBinding>(
    viewBinding = { layoutInflater, parent ->
        ItemTicketBinding.inflate(layoutInflater, parent, false)
    }
) {
    bind {
        binding.apply {
            badge.apply {
                visibility = if (item.badge.isEmpty()) View.GONE else View.VISIBLE
                binding.badge.text = item.badge
            }
            price.text = item.price
            departureTime.text = item.departureTime
            departureAirport.text = item.departureAirport
            arrivalTime.text = item.arrivalTime
            arrivalAirport.text = item.arrivalAirport
            flightTime.text = item.flightTime
            root.setOnClickListener { onItemClick(item) }
        }
    }
}