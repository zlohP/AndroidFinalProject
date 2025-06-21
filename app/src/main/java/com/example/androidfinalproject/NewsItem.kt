package com.example.androidfinalproject

import com.google.gson.annotations.SerializedName

data class NewsItem(
    val title: String?,    // nullable 로 바꿈
    val content: String?
)