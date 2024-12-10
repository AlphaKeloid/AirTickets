package io.captaingaga.airtickets.effective.mobile.main.ui

import android.net.Uri
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.captaingaga.airtickets.effective.mobile.main.components.UIRecommendedItem
import io.captaingaga.airtickets.effective.mobile.main.components.stub.mapRecommendedImageFromAssets
import io.captaingaga.airtickets.effective.mobile.main.databinding.ItemRecommendedBinding

fun recommendedAdapter(
    onItemClick: (UIRecommendedItem) -> Unit
) = adapterDelegateViewBinding<UIRecommendedItem, UIRecommendedItem, ItemRecommendedBinding>(
    viewBinding = { layoutInflater, parent ->
        ItemRecommendedBinding.inflate(layoutInflater, parent, false)
    }
) {
    bind {
        binding.root.setOnClickListener {
            onItemClick(item)
        }

        Glide.with(binding.root.context)
            .load(Uri.parse(item.id.mapRecommendedImageFromAssets()))
            .into(binding.destinationIcon)

        binding.destinationTitle.text = item.destinationTitle
    }
}