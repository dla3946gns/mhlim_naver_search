package com.example.mhlim_search.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class MovieFeed(val items: MutableList<SearchData>)

@Entity(tableName = "search_table")
data class SearchData (
    val title : String?,
    val link : String?,
    val image : String?,
    val subtitle : String?,
    val pubDate : String?,
    val director : String?,
    val actor : String?,
    val userRating : String?
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}