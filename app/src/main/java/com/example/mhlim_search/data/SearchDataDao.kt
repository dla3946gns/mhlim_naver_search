package com.example.mhlim_search.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchDataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSearchWord(searchData: SearchData)

    @Query("SELECT * FROM search_table ORDER BY id ASC")
    fun getAllData(): LiveData<MutableList<SearchData>>

}