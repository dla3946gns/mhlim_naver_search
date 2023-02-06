package com.example.mhlim_search.network

import com.example.mhlim_search.data.MovieFeed
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

object RetrofitAPI {
    private const val BASE_URL = "https://openapi.naver.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient(AppInterceptor()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiRequest: ApiRequest by lazy {
        retrofit.create(ApiRequest::class.java)
    }

    private fun provideOkHttpClient(interceptor: AppInterceptor): OkHttpClient
    = OkHttpClient.Builder().run {
        addInterceptor(interceptor)
            .build()
    }

    class AppInterceptor: Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("X-Naver-Client-Id", "PGdGCxgfeZfnp1Tm9AxY")
                .addHeader("X-Naver-Client-Secret", "kghuK9MP7w")
                .build()
            proceed(newRequest)
        }
    }
}

interface ApiRequest {
    @GET("v1/search/movie.json")
    suspend fun getMovieList(
        @Query("query") searchWord: String,
        @Query("display") display: String,
        @Query("start") start: String,
        @Query("genre") genre: String
    ): MovieFeed
}