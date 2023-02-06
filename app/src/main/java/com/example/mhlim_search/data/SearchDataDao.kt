package com.example.mhlim_search.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchDataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSearchWord(searchData: RecentWordData)

    @Query("SELECT * FROM recent_search_table")
    fun getAllData(): LiveData<MutableList<RecentWordData>>

}