package com.example.mhlim_search.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mhlim_search.R
import com.example.mhlim_search.adapter.RecentSearchDataListAdapter
import com.example.mhlim_search.data.SearchData
import com.example.mhlim_search.data.SearchWordViewModel
import com.example.mhlim_search.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RecentSearchActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecentSearchDataListAdapter

    @SuppressLint("notifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_search_word)

        recyclerView = findViewById(R.id.rv_recent_word)

        adapter = RecentSearchDataListAdapter()
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