package com.example.mhlim_search.activity

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mhlim_search.adapter.SearchResultPagingAdapter
import com.example.mhlim_search.data.*
import com.example.mhlim_search.databinding.ActivityMainBinding
import com.example.mhlim_search.decoration.SearchListItemDecoration
import com.example.mhlim_search.`interface`.ItemClickListener
import com.example.mhlim_search.viewmodel.SearchDataViewModelFactory
import com.example.mhlim_search.viewmodel.SearchResultViewModel
import com.example.mhlim_search.viewmodel.SearchWordViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import okhttp3.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pagingAdapter: SearchResultPagingAdapter
    private lateinit var searchWordViewModel: SearchWordViewModel
    private lateinit var searchResultViewModel: SearchResultViewModel
    private lateinit var onActivityResult: ActivityResultLauncher<Intent>

    @SuppressWarnings("notifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        searchWordViewModel = ViewModelProvider(this, SearchWordViewModel.Factory(application)).get(
            SearchWordViewModel::class.java)
        pagingAdapter = SearchResultPagingAdapter()
        pagingAdapter.setOpenBrowserListener(object : ItemClickListener {
            override fun onItemClick(str: String) {
                val openUrl = Intent(Intent.ACTION_VIEW)
                openUrl.data = Uri.parse(str)
                startActivity(openUrl)
            }
        })

        binding.clSearch.setOnClickListener {
            if (binding.etSearch.text != null && binding.etSearch.text.toString().isNotEmpty()) {
                searchMovie(binding.etSearch.text.toString())
            } else {
                Toast.makeText(this, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            hideKeyboard()
        }

        binding.clRecentSearch.setOnClickListener {
            val intent = Intent(this, RecentSearchActivity::class.java)
            onActivityResult.launch(intent)
        }

        binding.etSearch.setOnEditorActionListener(object : OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchMovie(binding.etSearch.text.toString())
                    return true
                }
                return false
            }
        })

        onActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
            if (result.resultCode == RESULT_OK) {
                val recentSearchWord = result.data?.getStringExtra("recent_search_word").toString()
                binding.etSearch.setText(recentSearchWord.toCharArray(), 0, recentSearchWord.length)
                searchMovie(recentSearchWord)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        requestHideKeyboard(ev)
        return super.dispatchTouchEvent(ev)
    }

    private fun searchMovie(word: String?) {
        // 검색어 Room DB에 추가
        if (word != null && word.isNotEmpty()) {
            val recentWordData = RecentWordData(word)
            searchWordViewModel.addSearchWord(recentWordData)

            while (binding.rvSearchList.itemDecorationCount > 0) {
                binding.rvSearchList.removeItemDecorationAt(0);
            }
            binding.rvSearchList.addItemDecoration(SearchListItemDecoration(this@MainActivity))
            binding.rvSearchList.adapter = pagingAdapter
            searchResultViewModel = ViewModelProvider(this, SearchDataViewModelFactory())[SearchResultViewModel::class.java]
            searchResultViewModel.searchWord = word

            lifecycleScope.launch {
                pagingAdapter.refresh()
                searchResultViewModel.data.collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }
    }

    private fun hideKeyboard() {
        if (binding.etSearch.text != null && binding.etSearch.text.toString().isNotEmpty()) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    private fun requestHideKeyboard(motionEvent: MotionEvent?) {

        val focusView = currentFocus
        if (focusView != null && motionEvent != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x: Int = motionEvent.x.toInt()
            val y: Int = motionEvent.y.toInt()
            if (!rect.contains(x, y)) {
                hideKeyboard()
                focusView.clearFocus()
            }
        }

    }

}