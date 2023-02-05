package com.example.mhlim_search.data

import androidx.lifecycle.LiveData

class SearchWordRepository(private val searchWordDao: SearchDataDao) {

    val getAllData: LiveData<MutableList<SearchData>> = searchWordDao.getAllData()

    suspend fun addSearchWord(searchData: SearchData) {
        searchWordDao.addSearchWord(searchData)
    }

}