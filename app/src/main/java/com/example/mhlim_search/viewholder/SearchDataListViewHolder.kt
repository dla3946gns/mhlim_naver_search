package com.example.mhlim_search.viewholder

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mhlim_search.R
import com.example.mhlim_search.data.SearchData

class SearchDataListViewHolder(val context: Context, itemView: View): RecyclerView.ViewHolder(itemView) {

    private val ivThumbnail: AppCompatImageView = itemView.findViewById(R.id.iv_thumbnail)
    private val tvTitle: TextView = itemView.findViewById(R.id.tv_movie_title)
    private val tvReleaseDate: TextView = itemView.findViewById(R.id.tv_movie_release_date)
    private val tvUserRating: TextView = itemView.findViewById(R.id.tv_movie_mark)

    fun bindData(searchData: SearchData?) {
        if (searchData != null) {
            if (searchData.image != null && searchData.image.isNotEmpty()) {
                Glide.with(context).load(searchData.image).into(ivThumbnail)
            }

            if (searchData.title != null && searchData.title.isNotEmpty()) {
                tvTitle.text = searchData.title
            }

            if (searchData.pubDate != null && searchData.pubDate.isNotEmpty()) {
                tvReleaseDate.text = searchData.pubDate
            }

            if (searchData.userRating != null && searchData.userRating.isNotEmpty()) {
                tvUserRating.text = searchData.userRating
            }
        }
    }

}