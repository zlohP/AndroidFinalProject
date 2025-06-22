package com.example.androidfinalproject

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidfinalproject.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //알림
        Firebase.messaging.token.addOnSuccessListener {
            Log.d("25android", it)
        }



        val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if(it.all {permission -> permission.value != true}){
                Toast.makeText(this,"permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(
                this,
                "android.permission.POST_NOTIFICATIONS"
            ) != PackageManager.PERMISSION_GRANTED){
                //승인 요청
                permissionLauncher.launch(arrayOf("android.permission.POST_NOTIFICATIONS"))

            }
        }

        //
        binding.loginButton.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "아이디와 비밀번호를 모두 입력해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            MyApplication.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task ->
                    binding.etEmail.text.clear()
                    binding.etPassword.text.clear()
                    if(task.isSuccessful){
                        if(MyApplication.checkAuth()){

                            MyApplication.email = email
                            val intent = Intent(this, SchedulerActivity::class.java)
                            startActivity(intent)
                        }else {
                            Toast.makeText(baseContext, "전송된 메일로 이메일 인증이 되지 않았습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()

                    }
                }
        }

        binding.signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

}

