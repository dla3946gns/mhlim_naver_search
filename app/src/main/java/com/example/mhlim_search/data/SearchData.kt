package com.example.mhlim_search.data

/**
 * 영화 검색 결과 데이터 리스트
 *
 * @author Myeong Hoon Lim on 2023-02-08
 */
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