package com.example.mhlim_search.data

import androidx.lifecycle.LiveData

/**
 * 최근 검색 이력 Database Repository 클래스
 *
 * @author Myeong Hoon Lim on 2023-02-08
 */
class SearchWordRepository(private val searchWordDao: SearchDataDao) {

    val getAllData: LiveData<MutableList<RecentWordData>> = searchWordDao.getAllData()

    suspend fun addSearchWord(searchData: RecentWordData) {
        searchWordDao.addSearchWord(searchData)
    }

}