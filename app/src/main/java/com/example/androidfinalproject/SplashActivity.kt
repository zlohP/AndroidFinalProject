package com.example.androidfinalproject

import android.content.Intent
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.androidfinalproject.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // val binding = ActivitySplashBinding.inflate(layoutInflater)
        // setContentView(binding.root)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val prefs = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
            val autoLogin = prefs.getBoolean("auto_login_enabled", false)


            val user = MyApplication.auth.currentUser
            val isVerified = user?.isEmailVerified == true

            val next = if (autoLogin && user != null && isVerified) {
                SchedulerActivity::class.java
            } else {
                MainActivity::class.java
            }

            startActivity(Intent(this, next))
            finish()
        }, 800)  // 원하는 딜레이(ms)
    }
}
