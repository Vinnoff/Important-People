package test.dev.importantpeople.presentation

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpaceItemDecoration(@Px private val spacing: Int, private val includeEdge: Boolean = true) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        when (parent.layoutManager) {
            is GridLayoutManager -> outRect.setForGrid(parent, view)
            is LinearLayoutManager -> outRect.setForLinear(parent, view)
        }
    }

    private fun Rect.setForGrid(parent: RecyclerView, view: View) {
        val position = parent.getChildAdapterPosition(view) // item position
        val spanCount = (parent.layoutManager as GridLayoutManager).spanCount
        val column = position % spanCount // item column
        if (includeEdge) {
            left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
            if (position < spanCount) { // top edge
                top = spacing
            }
            bottom = spacing // item bottom
        } else {
            left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
            right = spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                top = spacing // item top
            }
        }
    }

    private fun Rect.setForLinear(parent: RecyclerView, view: View) {
        val position = parent.getChildAdapterPosition(view) // item position
        when ((parent.layoutManager as LinearLayoutManager).orientation) {
            RecyclerView.HORIZONTAL -> {
                top = if (includeEdge) spacing else 0
                bottom = if (includeEdge) spacing else 0
                left = if (position == 0) spacing else 0
                right = spacing
            }
            RecyclerView.VERTICAL -> {
                left = if (includeEdge) spacing else 0
                right = if (includeEdge) spacing else 0
                top = if (position == 0) spacing else 0
                bottom = spacing
            }
        }
    }

}