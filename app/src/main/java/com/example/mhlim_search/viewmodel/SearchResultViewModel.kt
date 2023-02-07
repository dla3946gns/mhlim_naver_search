package com.example.mhlim_search.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mhlim_search.data.SearchData
import com.example.mhlim_search.`interface`.SearchResultValidListener
import com.example.mhlim_search.network.RetrofitAPI
import com.example.mhlim_search.paging.PagingSource
import kotlinx.coroutines.flow.Flow

class SearchResultViewModel: ViewModel() {

    var data: Flow<PagingData<SearchData>>
    lateinit var searchWord: String

    init {
        data = Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PagingSource(RetrofitAPI.apiRequest, searchWord) }
        ).flow.cachedIn(viewModelScope)
    }

}

class SearchDataViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchResultViewModel() as T
    }
}