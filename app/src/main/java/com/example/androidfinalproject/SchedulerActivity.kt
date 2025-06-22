package com.example.androidfinalproject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.androidfinalproject.databinding.ActivitySchedulerBinding
import com.google.android.material.tabs.TabLayoutMediator

class SchedulerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySchedulerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = getSharedPreferences("AppSettings", MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        super.onCreate(savedInstanceState)
        binding = ActivitySchedulerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        val tabTitles = listOf("일정", "뉴스", "감정일기")

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val tabView = LayoutInflater.from(this).inflate(R.layout.custom_tab, null)
            val textView = tabView.findViewById<TextView>(R.id.tabText)
            textView.text = tabTitles[position]
            tab.customView = tabView
        }.attach()
    }
}
