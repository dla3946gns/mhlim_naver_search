package com.example.mhlim_search.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecentWordData::class], version = 1, exportSchema = false)
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
                        AppData.ROOM_DATABASE_SEARCH_WORD.data
                    ).build()
                }
            }
            return instance
        }
    }

}