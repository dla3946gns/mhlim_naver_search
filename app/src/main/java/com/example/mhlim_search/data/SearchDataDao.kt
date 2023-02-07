package com.example.mhlim_search.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchDataDao {

    // 검색어가 PrimaryKey이기에 겹치는 단어면 치환시킨다.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearchWord(searchData: RecentWordData)

    // timestamp 값을 기준으로 내림차순으로 정렬하여 갖고 오기에 최근 검색 순으로 정렬된다.
    @Query("SELECT * FROM recent_search_table order by timestamp desc")
    fun getAllData(): LiveData<MutableList<RecentWordData>>

}