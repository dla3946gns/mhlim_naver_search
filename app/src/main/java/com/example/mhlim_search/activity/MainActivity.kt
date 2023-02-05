package com.example.mhlim_search.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.mhlim_search.R
import com.example.mhlim_search.adapter.SearchDataListAdapter
import com.example.mhlim_search.data.MovieFeed
import com.example.mhlim_search.data.SearchData
import com.example.mhlim_search.data.SearchWordViewModel
import com.example.mhlim_search.databinding.ActivityMainBinding
import com.example.mhlim_search.decoration.SearchListItemDecoration
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchDataListAdapter
    private lateinit var searchWordViewModel: SearchWordViewModel

    @SuppressWarnings("notifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SearchDataListAdapter(this)
        searchWordViewModel = ViewModelProvider(this, SearchWordViewModel.Factory(application)).get(SearchWordViewModel::class.java)

        binding.clSearch.setOnClickListener {
            if (binding.etSearch.text != null && binding.etSearch.text.toString().isNotEmpty()) {
                // 검색어 Room DB에 추가
                val searchWordData = SearchData(
                    binding.etSearch.text.toString(),
                    "", "","","","","",""
                )
                searchWordViewModel.addSearchWord(searchWordData)

                setNaverSearchResult(binding.etSearch.text.toString())
            } else {
                Toast.makeText(this, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            hideKeyboard()
        }

        binding.clRecentSearch.setOnClickListener {
            val intent = Intent(this, RecentSearchActivity::class.java)
            startActivity(intent)
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun setNaverSearchResult(searchWord: String) {
        val url = URL("https://openapi.naver.com/v1/search/movie.json?query=${searchWord}&display=10&start=1&genre=")
        val request = Request.Builder()
            .url(url)
            .addHeader("X-Naver-Client-Id", getString(R.string.str_naver_client_id))
            .addHeader("X-Naver-Client-Secret", getString(R.string.str_naver_client_secret))
            .method("GET", null)
            .build()
        var movieFeedData = MovieFeed(mutableListOf())

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                println("Success to execute request: $body")

                val gson = GsonBuilder().create()
                movieFeedData = gson.fromJson(body, MovieFeed::class.java)

                CoroutineScope(Dispatchers.Main).launch {
                    while (binding.rvSearchList.getItemDecorationCount() > 0) {
                        binding.rvSearchList.removeItemDecorationAt(0);
                    }
                    binding.rvSearchList.addItemDecoration(SearchListItemDecoration(this@MainActivity))

                    adapter.setData(movieFeedData.items)
                    binding.rvSearchList.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun hideKeyboard() {
        if (binding.etSearch.text != null && binding.etSearch.text.toString().isNotEmpty()) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
}