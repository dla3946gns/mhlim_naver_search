package com.example.mhlim_search.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.security.Timestamp

@Entity(tableName = "recent_search_table")
data class RecentWordData(

    @PrimaryKey(autoGenerate = false)
    var title: String,
    var timestamp: Long

)