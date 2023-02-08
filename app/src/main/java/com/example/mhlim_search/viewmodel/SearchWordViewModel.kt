package com.example.mhlim_search.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.mhlim_search.data.RecentWordData
import com.example.mhlim_search.data.SearchWordDatabase
import com.example.mhlim_search.data.SearchWordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 최근 검색 이력 ViewModel Class
 *
 * @author Myeong Hoon Lim on 2023-02-08
 */
class SearchWordViewModel(application: Application): AndroidViewModel(application) {

    val getAllData: LiveData<MutableList<RecentWordData>>
    private val repository: SearchWordRepository

    init {
        val searchWordDao = SearchWordDatabase.getInstance(application)!!.searchWordDao()
        repository = SearchWordRepository(searchWordDao)
        getAllData = repository.getAllData
    }

    fun addSearchWord(searchData: RecentWordData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSearchWord(searchData)
        }
    }

    class Factory(val application: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchWordViewModel(application) as T
        }
    }

}