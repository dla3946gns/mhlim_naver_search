package com.example.mhlim_search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mhlim_search.data.SearchData
import com.example.mhlim_search.network.RetrofitAPI
import com.example.mhlim_search.paging.PagingSource
import kotlinx.coroutines.flow.Flow

/**
 * 검색 결과 ViewModel Class
 *
 * @author Myeong Hoon Lim on 2023-02-08
 */
class SearchResultViewModel: ViewModel() {

    lateinit var data: Flow<PagingData<SearchData>>
    private var searchWord: String = ""

    fun setQuery(str: String) {
        searchWord = str
    }

    fun requestResult() {
        data = Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PagingSource(RetrofitAPI.apiRequest, searchWord) }
        ).flow.cachedIn(viewModelScope)
    }

}