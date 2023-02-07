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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.map
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.mhlim_search.R
import com.example.mhlim_search.adapter.SearchResultLoadStateAdapter
import com.example.mhlim_search.adapter.SearchResultPagingAdapter
import com.example.mhlim_search.data.*
import com.example.mhlim_search.databinding.ActivityMainBinding
import com.example.mhlim_search.decoration.SearchListItemDecoration
import com.example.mhlim_search.`interface`.ItemClickListener
import com.example.mhlim_search.utils.BackPressFinishHandler
import com.example.mhlim_search.viewmodel.SearchDataViewModelFactory
import com.example.mhlim_search.viewmodel.SearchResultViewModel
import com.example.mhlim_search.viewmodel.SearchWordViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import okhttp3.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pagingAdapter: SearchResultPagingAdapter

    private lateinit var searchWordViewModel: SearchWordViewModel       // 검색어 Room DB 저장용 ViewModel
    private lateinit var searchResultViewModel: SearchResultViewModel   // 검색 결과 ViewModel

    private lateinit var inputMethodManager: InputMethodManager
    private lateinit var backPressFinishHandler: BackPressFinishHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        pagingAdapter = SearchResultPagingAdapter()
        pagingAdapter.setOpenBrowserListener(itemClickListener)

        backPressFinishHandler = BackPressFinishHandler(this)

        initViewModel()

        initOnClickListener()

        binding.etSearch.postDelayed(Runnable {
            binding.etSearch.isFocusableInTouchMode = true
            binding.etSearch.requestFocus()
            inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(binding.etSearch, 0)
        }, 300)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        requestHideKeyboard(ev)
        return super.dispatchTouchEvent(ev)
    }

    override fun onBackPressed() {
        backPressFinishHandler.onBackPressed()
    }

    /**
     * ViewModel 초기화 메소드
     * <p>
     *     Created by Myeong Hoon Lim on 2023-02-07
     * </p>
     */
    private fun initViewModel() {
        searchWordViewModel = ViewModelProvider(this, SearchWordViewModel.Factory(application)).get(SearchWordViewModel::class.java)
        searchResultViewModel = ViewModelProvider(this, SearchDataViewModelFactory())[SearchResultViewModel::class.java]
    }

    /**
     * 클릭 이벤트 세팅 메소드
     * <p>
     *     Created by Myeong Hoon Lim on 2023-02-07
     * </p>
     */
    private fun initOnClickListener() {
        binding.clSearch.setOnClickListener {
            if (binding.etSearch.text != null && binding.etSearch.text.toString().isNotEmpty()) {
                searchMovie(binding.etSearch.text.toString())
                hideKeyboard()
            } else {
                Toast.makeText(this, resources.getString(R.string.str_write_search_word), Toast.LENGTH_SHORT).show()
                binding.etSearch.isFocusableInTouchMode = true
                binding.etSearch.requestFocus()
            }
        }

        binding.clRecentSearch.setOnClickListener {
            val intent = Intent(this, RecentSearchActivity::class.java)
            onActivityResult.launch(intent)
        }

        binding.etSearch.setOnEditorActionListener(object : OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchMovie(binding.etSearch.text.toString())
                    hideKeyboard()
                    return true
                }
                return false
            }
        })

        binding.tvRestart.setOnClickListener {
            pagingAdapter.retry()
        }
    }

    /**
     * Naver 검색 메소드
     *
     * <p>
     *     Created by Myeong Hoon Lim on 2023-02-07
     * </p>
     */
    private fun searchMovie(word: String?) {
        // 검색어 Room DB에 추가
        if (word != null && word.isNotEmpty()) {
            val recentWordData = RecentWordData(word, System.currentTimeMillis() / 1000)
            searchWordViewModel.addSearchWord(recentWordData)

            // 데코레이션 세팅
            while (binding.rvSearchList.itemDecorationCount > 0) {
                binding.rvSearchList.removeItemDecorationAt(0);
            }
            binding.rvSearchList.addItemDecoration(SearchListItemDecoration(this@MainActivity))
            pagingAdapter.addLoadStateListener(loadStateListener)

            binding.rvSearchList.adapter = pagingAdapter.withLoadStateHeaderAndFooter(
                header = SearchResultLoadStateAdapter { pagingAdapter.retry() },
                footer = SearchResultLoadStateAdapter { pagingAdapter.retry() }
            )

            binding.rvSearchList.setHasFixedSize(true)

            searchResultViewModel.searchWord = word

            lifecycleScope.launch {
                pagingAdapter.refresh()
                searchResultViewModel.data.collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }
    }

    /**
     * 키보드 숨기는 메소드
     * <p>
     *     Created by Myeong Hoon Lim on 2023-02-07
     * </p>
     */
    private fun hideKeyboard() {
        if (binding.etSearch.text != null && currentFocus != null) {
            val view = currentFocus
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }

    /**
     * 검색창 이외 화면 클릭 시 키보드 숨기는 메소드
     * <p>
     *     Created by Myeong Hoon Lim on 2023-02-07
     * </p>
     */
    private fun requestHideKeyboard(motionEvent: MotionEvent?) {
        val focusView = currentFocus
        if (focusView != null && motionEvent != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = motionEvent.x.toInt()
            val y = motionEvent.y.toInt()
            if (!rect.contains(x, y)) {
                hideKeyboard()
                focusView.clearFocus()
            }
        }
    }
    
    // 영화 검색 결과 아이템 클릭 리스너
    private val itemClickListener = object : ItemClickListener {
        override fun onItemClick(str: String) {
            // 브라우저 열기
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse(str)
            startActivity(openUrl)
        }
    }

    // 리스트 로딩 상태 리스너
    private val loadStateListener = object : (CombinedLoadStates) -> Unit {
        override fun invoke(combinedLoadStates: CombinedLoadStates) {
            binding.apply {
                progressBar.isVisible = combinedLoadStates.source.refresh is LoadState.Loading

                rvSearchList.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading

                llRestart.isVisible = combinedLoadStates.source.refresh is LoadState.Error

                if (combinedLoadStates.source.refresh is LoadState.NotLoading
                    && combinedLoadStates.append.endOfPaginationReached
                    && pagingAdapter.itemCount < 1) {
                    rvSearchList.isVisible = false
                    tvSearchResultNone.isVisible = true
                } else {
                    rvSearchList.isVisible = true
                    tvSearchResultNone.isVisible = false
                }
            }
        }
    }

    // 최근 검색어 클릭 후 검색 결과를 가져오기 위한 ActivityResultLauncher
    private val onActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val recentSearchWord = result.data?.getStringExtra("recent_search_word").toString()
            binding.etSearch.setText(recentSearchWord.toCharArray(), 0, recentSearchWord.length)
            searchMovie(recentSearchWord)
        }
    }

}