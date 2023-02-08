package com.example.mhlim_search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.mhlim_search.data.SearchData
import com.example.mhlim_search.databinding.ItemSearchDataListBinding
import com.example.mhlim_search.`interface`.ItemClickListener
import com.example.mhlim_search.viewholder.SearchResultViewHolder

/**
 * 검색 결과 페이징 아답터
 *
 * @author Myeong Hoon Lim on 2023-02-08
 */
class SearchResultPagingAdapter: PagingDataAdapter<SearchData, SearchResultViewHolder>(diffCallback) {

    private lateinit var mItemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(
            ItemSearchDataListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            mItemClickListener
        )
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<SearchData>() {
            override fun areItemsTheSame(oldItem: SearchData, newItem: SearchData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SearchData, newItem: SearchData): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun setOpenBrowserListener(itemClickListener: ItemClickListener) {
        mItemClickListener = itemClickListener
    }

}