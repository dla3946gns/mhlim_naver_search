package com.example.mhlim_search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.mhlim_search.R
import com.example.mhlim_search.data.SearchData
import com.example.mhlim_search.viewholder.RecentSearchDataListViewHolder

class RecentSearchDataListAdapter: Adapter<RecentSearchDataListViewHolder>() {

    private var dataList = mutableListOf<SearchData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentSearchDataListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent_search_history, parent, false)
        return RecentSearchDataListViewHolder(parent.context, view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecentSearchDataListViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    fun setData(searchDataList: MutableList<SearchData>) {
        dataList = searchDataList
    }

}