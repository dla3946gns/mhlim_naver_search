package com.example.mhlim_search.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mhlim_search.R
import com.example.mhlim_search.adapter.RecentSearchDataListAdapter
import com.example.mhlim_search.`interface`.ItemClickListener
import com.example.mhlim_search.viewmodel.SearchWordViewModel

class RecentSearchActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecentSearchDataListAdapter

    @SuppressLint("notifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_search_word)

        recyclerView = findViewById(R.id.rv_recent_word)

        adapter = RecentSearchDataListAdapter()
        adapter.setOnItemClickListener(object : ItemClickListener {
            override fun onItemClick(str: String) {
                // 검색 페이지에서 검색
                val intent = Intent(this@RecentSearchActivity, MainActivity::class.java).apply {
                    putExtra("recent_search_word", str)
                }
                setResult(RESULT_OK, intent)
                if (!isFinishing) {
                    finish()
                }
            }
        })

        val searchWordViewModel = ViewModelProvider(this, SearchWordViewModel.Factory(application)).get(SearchWordViewModel::class.java)

        searchWordViewModel.getAllData.observe(this@RecentSearchActivity, Observer {
            if (it.isNotEmpty()) {
                adapter.setData(it)
            }
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        })
    }

}