package com.example.mhlim_search.viewholder

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mhlim_search.R
import com.example.mhlim_search.data.SearchData

class RecentSearchDataListViewHolder(val context: Context, itemView: View): ViewHolder(itemView) {

    private val tvRecentWord: TextView = itemView.findViewById(R.id.tv_history)

    fun bindData(searchData: SearchData) {
        if (searchData.title != null && searchData.title.isNotEmpty()) {
            tvRecentWord.text = searchData.title
        }
    }

}