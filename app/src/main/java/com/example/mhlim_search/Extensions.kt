package com.example.mhlim_search

import android.os.Build
import android.text.Html

/**
 * 버전 별 문자열에 있는 Html 태그 삭제 후 리턴시키는 메소드
 *
 * @author Myeong Hoon Lim on 2023-02-08
 */
fun String.removeHtml(): String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        return Html.fromHtml(this).toString()
    }
}