package com.example.androidfinalproject

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import kotlinx.coroutines.CancellationException

class NewsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var newsList: List<NewsItem>  // 🔹 추가됨
    private val API_KEY =
        "2RVX1Q+9QQe/DTaBJ1I9GYPpw+VdtQAK0XY2guQlXNurdsFN0UA6OZsCrAwU3QV2+nGrtXC7KRW5JpGWxGCZyQ=="

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recyclerViewNews)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val ytbBtn = view.findViewById<Button>(R.id.ytb_btn)  // 🔹 버튼 찾기

        lifecycleScope.launch {
            try {
                val response: Response<ResponseBody> = RetrofitInstance.api.getRawNews(API_KEY)
                val rawJson = response.body()?.string()

                Log.e("뉴스응답_RAW", rawJson ?: "rawJson null임")

                if (!response.isSuccessful || rawJson.isNullOrBlank()) {
                    Log.e("뉴스응답_RAW", rawJson ?: "응답 실패 또는 비어 있음")
                    Toast.makeText(requireContext(), "API 오류:\n${rawJson}", Toast.LENGTH_LONG).show()
                    return@launch
                }

                val gson = GsonBuilder().setLenient().create()
                val parsed = gson.fromJson(rawJson, NewsResponse::class.java)
                val rawList = parsed.gyeongnamnewsinfolist.body.items.item ?: emptyList()

                newsList = rawList.map {
                    NewsItem(
                        title = it.data_title ?: "제목 없음",
                        content = it.data_content ?: "내용 없음"
                    )
                }

                adapter = NewsAdapter(newsList) { news -> showDialog(news) }
                recyclerView.adapter = adapter

                // 🔹 버튼 클릭 시 유튜브 검색 링크 열기
                ytbBtn.setOnClickListener {
                    val latestTitle = newsList.firstOrNull()?.title ?: "경남 뉴스"
                    val keyword = "$latestTitle 관련 영상"
                    val url = "https://www.youtube.com/results?search_query=${Uri.encode(keyword)}"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }

            } catch (e: Exception) {
                if (e is CancellationException) return@launch
                Log.e("뉴스응답", "예외 발생", e)
                Toast.makeText(requireContext(), "예외 발생: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showDialog(item: NewsItem) {
        val cleanedText = HtmlCompat.fromHtml(
            item.content ?: "내용 없음",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        ).toString()

        AlertDialog.Builder(requireContext())
            .setTitle(item.title)
            .setMessage(cleanedText)
            .setPositiveButton("닫기", null)
            .show()
    }
}
