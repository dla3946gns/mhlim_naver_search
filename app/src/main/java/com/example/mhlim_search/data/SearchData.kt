package com.example.mhlim_search.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class MovieFeed(
    val total: Int,
    val items: MutableList<SearchData>
)

data class SearchData (
    val title : String?,
    val link : String?,
    val image : String?,
    val subtitle : String?,
    val pubDate : String?,
    val director : String?,
    val actor : String?,
    val userRating : String?
)