package com.example.mhlim_search.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SearchData::class], version = 1, exportSchema = false)
abstract class SearchWordDatabase: RoomDatabase() {

    abstract fun searchWordDao(): SearchDataDao

    companion object {
        @Volatile
        private var instance: SearchWordDatabase? = null

        fun getInstance(context: Context): SearchWordDatabase? {
            if (instance == null) {
                synchronized(SearchWordDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SearchWordDatabase::class.java,
                        "search_word_database"
                    ).build()
                }
            }
            return instance
        }
    }

}