package com.example.mhlim_search.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_search_table")
data class RecentWordData(
    @PrimaryKey(autoGenerate = false)
    var title: String
)