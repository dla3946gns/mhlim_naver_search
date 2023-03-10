package com.example.mhlim_search.data

/**
 * 공통 앱 데이터 변수 모음
 *
 * @author Myeong Hoon Lim on 2023-02-08
 */
enum class AppData(
    val data: String
) {

    // Naver 검색 API [Start]
    NAVER_CLIENT_ID_HEADER_KEY("X-Naver-Client-Id"),
    NAVER_CLIENT_SECRET_HEADER_KEY("X-Naver-Client-Secret"),
    NAVER_CLIENT_ID_HEADER_VALUE("PGdGCxgfeZfnp1Tm9AxY"),
    NAVER_CLIENT_SECRET_HEADER_VALUE("kghuK9MP7w"),
    // Naver 검색 API [End]

    // Room Database Name [Start]
    ROOM_DATABASE_SEARCH_WORD("search_word_database")
    // Room Database Name [End]

}