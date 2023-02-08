package com.example.mhlim_search.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.security.Timestamp

/**
 * 최근 검색 이력 데이터 클래스
 *
 * @author Myeong Hoon Lim on 2023-02-08
 */
@Entity(tableName = "recent_search_table")
data class RecentWordData(

    @PrimaryKey(autoGenerate = false)
    var title: String,
    var timestamp: Long

)