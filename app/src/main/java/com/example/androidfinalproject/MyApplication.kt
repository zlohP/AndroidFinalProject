package com.example.androidfinalproject

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
    }
}