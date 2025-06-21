package com.example.androidfinalproject

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://apis.data.go.kr/"

    private val gson = GsonBuilder()
        .setLenient() // 💥 핵심 추가: 비정상 JSON 허용
        .create()

    val api: NewsService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)) // 수정됨
            .build()
            .create(NewsService::class.java)
    }
}
