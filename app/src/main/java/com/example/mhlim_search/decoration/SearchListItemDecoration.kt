package com.example.mhlim_search.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.mhlim_search.R
import org.checkerframework.checker.interning.qual.CompareToMethod

/**
 * 검색 결과 리스트 Decoration Class
 * 
 * @author Myeong Hoon Lim on 2023-02-08
 */
class SearchListItemDecoration(val context: Context): ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        if (position == 0) {
            outRect.top = context.resources.getDimensionPixelSize(R.dimen.size_20)
        }
        outRect.bottom = context.resources.getDimensionPixelSize(R.dimen.size_20)
    }

}