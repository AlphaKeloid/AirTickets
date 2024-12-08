package io.captaingaga.airtickets.effective.mobile.main.ui

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration

class OfferItemDecorator(private val context: Context) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL)
                left =
                    context.resources
                        .getDimension(io.captaingaga.airtickets.effective.mobile.common.R.dimen.space_medium)
                        .toInt()
            }
            if (parent.getChildAdapterPosition(view) == (state.itemCount - 1)) {
                right = context.resources
                    .getDimension(io.captaingaga.airtickets.effective.mobile.common.R.dimen.space_medium)
                    .toInt()
            }

            left = context.resources
                    .getDimension(io.captaingaga.airtickets.effective.mobile.common.R.dimen.space_large)
                    .toInt()
        }
    }
}