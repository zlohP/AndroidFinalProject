package com.example.androidfinalproject

data class NewsResponse(
    val gyeongnamnewsinfolist: GyeongnamNewsInfoList
)

data class GyeongnamNewsInfoList(
    val header: Header,
    val body: Body
)

data class Header(
    val resultCode: String,
    val resultMsg: String
)

data class Body(
    val items: Items
)

data class Items(
    val item: List<NewsItemRaw>
)

data class NewsItemRaw(
    val data_title: String,
    val data_content: String
)
