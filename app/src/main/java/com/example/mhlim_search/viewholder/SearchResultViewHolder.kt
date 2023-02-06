package com.example.mhlim_search.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mhlim_search.data.SearchData
import com.example.mhlim_search.databinding.ItemSearchDataListBinding
import com.example.mhlim_search.`interface`.ItemClickListener
import com.example.mhlim_search.removeHtml

class SearchResultViewHolder(
    private val binding: ItemSearchDataListBinding,
    private val itemClickListener: ItemClickListener
): RecyclerView.ViewHolder(binding.root) {

    var searchData: SearchData? = null

    fun bind(item: SearchData) {
        item.let {
            binding.tvMovieTitle.text = String.format("제목 : %s", item.title?.removeHtml())
            binding.tvMovieReleaseDate.text = String.format("출시일 : %s", item.pubDate)
            binding.tvUserRating.text = String.format("평점 : %s", item.userRating)
            Glide.with(binding.ivThumbnail.context)
                .load(it.image)
                .into(binding.ivThumbnail)

            binding.llMovie.setOnClickListener {
                if (item.link != null && item.link.isNotEmpty()) {
                    itemClickListener.onItemClick(item.link)
                }
            }

            searchData = item
        }
    }

}