package io.captaingaga.airtickets.effective.mobile.tickets.ui

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TicketItemDecorator(
    private val context: Context
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == (state.itemCount - 1)) {
                bottom = context.resources
                    .getDimension(io.captaingaga.airtickets.effective.mobile.common.R.dimen.space_48)
                    .toInt()
            }
            top = context.resources
                .getDimension(io.captaingaga.airtickets.effective.mobile.common.R.dimen.space_8)
                .toInt()
        }
    }
}