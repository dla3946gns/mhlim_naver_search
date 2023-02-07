package com.example.mhlim_search.paging

import android.util.Log
import androidx.paging.PagingState
import com.example.mhlim_search.data.SearchData
import com.example.mhlim_search.`interface`.SearchResultValidListener
import com.example.mhlim_search.network.ApiRequest

class PagingSource(
    private val service: ApiRequest,
    private val word: String
): androidx.paging.PagingSource<Int, SearchData>() {

    var itemCount: Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchData> {
        val page = params.key ?: 1

        return try {
            val response = service.getMovieList(
                word,
                "10",
                page.toString(),
                ""
            )

            itemCount += response.items.size

            val nextKey =
                if (itemCount == response.total) {
                    null
                } else {
                    page + 10
                }

            val searchDataList = response.items
            LoadResult.Page(
                data = searchDataList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchData>): Int? {
        return null
    }

}