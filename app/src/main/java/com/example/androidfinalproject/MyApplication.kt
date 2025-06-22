package com.example.androidfinalproject

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.multidex.MultiDexApplication
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MyApplication:MultiDexApplication() {
    companion object{
        lateinit var auth : FirebaseAuth
        var email: String?= null

        fun checkAuth(): Boolean {
            val currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email
                if(currentUser.isEmailVerified){
                    true
                }
                else{
                    false
                }
            } ?:let {false}
        }
    }

    override fun onCreate() {
        super.onCreate()

        auth = Firebase.auth

        // 모든 Activity가 생성될 때마다 배경을 바꿔주도록 콜백 등록
        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                // 빈 구현으로 남겨두어도 OK
            }
            override fun onActivityStarted(activity: Activity) {
                val prefs = activity
                    .getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
                val bg = prefs.getString("background_color", "pink")
                val resId = if (bg == "green")
                    R.color.appBackgroundGreen
                else
                    R.color.appBackground
                activity.findViewById<View>(R.id.rootLayout)
                    ?.setBackgroundColor(
                        ContextCompat.getColor(activity, resId)
                    )
            }
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }
}