package io.captaingaga.airtickets.effective.mobile.main.ui

import android.net.Uri
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.captaingaga.airtickets.effective.mobile.data.models.OfferModel
import io.captaingaga.airtickets.effective.mobile.main.components.stub.mapOfferImageFromAssets
import io.captaingaga.airtickets.effective.mobile.main.databinding.ItemOfferBinding

fun offerAdapter() = adapterDelegateViewBinding<OfferModel, OfferModel, ItemOfferBinding>(
    viewBinding = { layoutInflater, parent ->
        ItemOfferBinding.inflate(layoutInflater, parent, false)
    }
) {
    bind {
        binding.artistName.text = item.title
        binding.city.text = item.town
        binding.price.text = item.price

        Glide.with(binding.root.context)
            .load(Uri.parse(item.id.mapOfferImageFromAssets()))
            .into(binding.artistIcon)
    }
}
