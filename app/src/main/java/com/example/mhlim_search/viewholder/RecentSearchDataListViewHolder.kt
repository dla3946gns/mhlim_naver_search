package com.example.mhlim_search.viewholder

import android.content.Context
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mhlim_search.R
import com.example.mhlim_search.data.RecentWordData
import com.example.mhlim_search.`interface`.ItemClickListener

/**
 * 최근 검색 이력 리스트 ViewHolder
 *
 * @author Myeong Hoon Lim on 2023-02-08
 */
class RecentSearchDataListViewHolder(
    val context: Context,
    itemView: View,
    onItemClickListener: ItemClickListener
): ViewHolder(itemView) {

    private val tvRecentWord: TextView = itemView.findViewById(R.id.tv_history)
    private val itemClickListener = onItemClickListener

    fun bindData(searchData: RecentWordData) {
        if (searchData.title.isNotEmpty()) {
            tvRecentWord.text = searchData.title

            tvRecentWord.setOnClickListener {
                itemClickListener.onItemClick(searchData.title)
            }
        }
    }

}