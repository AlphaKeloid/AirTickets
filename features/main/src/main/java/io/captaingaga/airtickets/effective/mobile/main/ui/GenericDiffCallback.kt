package io.captaingaga.airtickets.effective.mobile.main.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import io.captaingaga.airtickets.effective.mobile.main.components.DelegateAdapterItem

class GenericDiffCallback<T : DelegateAdapterItem> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem::class == newItem::class && oldItem.id() == newItem.id()
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.content() == newItem.content()
    }
}
