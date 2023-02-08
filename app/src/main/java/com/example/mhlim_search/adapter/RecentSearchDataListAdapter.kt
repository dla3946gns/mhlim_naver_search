package com.example.mhlim_search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.mhlim_search.R
import com.example.mhlim_search.data.RecentWordData
import com.example.mhlim_search.`interface`.ItemClickListener
import com.example.mhlim_search.viewholder.RecentSearchDataListViewHolder

/**
 * 최근 검색 이력 리스트 아답터
 *
 * @author Myeong Hoon Lim on 2023-02-08
 */
class RecentSearchDataListAdapter: Adapter<RecentSearchDataListViewHolder>() {

    private var dataList = mutableListOf<RecentWordData>()
    private lateinit var mOnItemClickListener: ItemClickListener
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentSearchDataListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent_search_history, parent, false)
        return RecentSearchDataListViewHolder(parent.context, view, mOnItemClickListener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecentSearchDataListViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    fun setData(searchDataList: MutableList<RecentWordData>) {
        dataList = searchDataList
    }

    fun setOnItemClickListener(onItemClickListener: ItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

}