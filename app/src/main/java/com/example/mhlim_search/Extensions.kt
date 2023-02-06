package com.example.mhlim_search

import android.os.Build
import android.text.Html

fun String.removeHtml(): String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        return Html.fromHtml(this).toString()
    }
}