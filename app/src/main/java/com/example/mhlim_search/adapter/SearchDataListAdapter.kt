package com.example.mhlim_search.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.mhlim_search.R
import com.example.mhlim_search.data.SearchData
import com.example.mhlim_search.viewholder.SearchDataListViewHolder

class SearchDataListAdapter(val context: Context): Adapter<SearchDataListViewHolder>() {

    var movieDataList = mutableListOf<SearchData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchDataListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_search_data_list, parent, false)
        return SearchDataListViewHolder(context, view)
    }

    override fun getItemCount(): Int = movieDataList.size

    override fun onBindViewHolder(holder: SearchDataListViewHolder, position: Int) {
        holder.bindData(movieDataList[position])
    }

    fun setData(searchDataList: MutableList<SearchData>) {
        movieDataList = searchDataList
    }

}