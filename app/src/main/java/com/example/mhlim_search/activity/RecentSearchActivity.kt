package com.example.mhlim_search.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mhlim_search.R
import com.example.mhlim_search.adapter.RecentSearchDataListAdapter
import com.example.mhlim_search.data.IntentData
import com.example.mhlim_search.data.RecentWordData
import com.example.mhlim_search.databinding.ActivityMainBinding
import com.example.mhlim_search.databinding.ActivityRecentSearchWordBinding
import com.example.mhlim_search.`interface`.ItemClickListener
import com.example.mhlim_search.viewmodel.SearchWordViewModel

/**
 * 최근 검색 이력 Activity
 * <p>
 *
 */
class RecentSearchActivity: AppCompatActivity() {

    private val RECENT_WORDS_LIMIT_SIZE = 10

    private lateinit var binding: ActivityRecentSearchWordBinding
    private lateinit var adapter: RecentSearchDataListAdapter

    @SuppressLint("notifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecentSearchWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RecentSearchDataListAdapter()
        adapter.setOnItemClickListener(object : ItemClickListener {
            override fun onItemClick(str: String) {
                // 검색 페이지에서 검색
                val intent = Intent(this@RecentSearchActivity, MainActivity::class.java).apply {
                    putExtra(IntentData.RECENT_SEARCH_WORD_INTENT.intentKey, str)
                }
                setResult(RESULT_OK, intent)
                if (!isFinishing) {
                    finish()
                }
            }
        })

        val searchWordViewModel = ViewModelProvider(this, SearchWordViewModel.Factory(application))[SearchWordViewModel::class.java]

        searchWordViewModel.getAllData.observe(this@RecentSearchActivity, Observer {recentWordDataList ->
            if (recentWordDataList.isNotEmpty()) {
                if (recentWordDataList.size < RECENT_WORDS_LIMIT_SIZE) {
                    adapter.setData(recentWordDataList)
                } else {
                    val tmpList = mutableListOf<RecentWordData>()
                    for (i in 0 until RECENT_WORDS_LIMIT_SIZE) {
                        if (recentWordDataList[i].title.isNotEmpty()) {
                            tmpList.add(recentWordDataList[i])
                        }
                    }
                    adapter.setData(tmpList)
                }

                binding.rvRecentWord.adapter = adapter
                adapter.notifyDataSetChanged()

                binding.rvRecentWord.isVisible = true
                binding.tvResultNone.isVisible = false
            } else {
                binding.rvRecentWord.isVisible = false
                binding.tvResultNone.isVisible = true
            }
        })
    }

}