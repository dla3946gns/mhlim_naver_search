package com.example.mhlim_search.data

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchWordViewModel(application: Application): AndroidViewModel(application) {

    val getAllData: LiveData<MutableList<SearchData>>
    private val repository: SearchWordRepository

    init {
        val searchWordDao = SearchWordDatabase.getInstance(application)!!.searchWordDao()
        repository = SearchWordRepository(searchWordDao)
        getAllData = repository.getAllData
    }

    fun addSearchWord(searchData: SearchData) {
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