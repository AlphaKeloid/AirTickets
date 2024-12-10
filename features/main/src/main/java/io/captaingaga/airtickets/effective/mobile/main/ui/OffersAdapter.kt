package io.captaingaga.airtickets.effective.mobile.main.ui

import android.net.Uri
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.captaingaga.airtickets.effective.mobile.main.components.UiOfferItem
import io.captaingaga.airtickets.effective.mobile.main.components.stub.mapOfferImageFromAssets
import io.captaingaga.airtickets.effective.mobile.main.databinding.ItemOfferBinding

fun offerAdapter() = adapterDelegateViewBinding<UiOfferItem, UiOfferItem, ItemOfferBinding>(
    viewBinding = { layoutInflater, parent ->
        ItemOfferBinding.inflate(layoutInflater, parent, false)
    }
) {
    bind {
        binding.artistName.text = item.artistName
        binding.city.text = item.city
        binding.price.text = item.price

        Glide.with(binding.root.context)
            .load(Uri.parse(item.id.mapOfferImageFromAssets()))
            .into(binding.artistIcon)
    }
}
