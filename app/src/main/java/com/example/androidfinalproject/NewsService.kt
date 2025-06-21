package com.example.androidfinalproject

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("6480000/gyeongnamnewsinfo/gyeongnamnewsinfolist")
    suspend fun getRawNews(
        @Query("serviceKey") apiKey: String,
        @Query("numOfRows") numOfRows: Int = 5,
        @Query("pageNo") pageNo: Int = 1,
        @Query("resultType") resultType: String = "json"
    ): Response<ResponseBody>
}