package com.example.androidfinalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidfinalproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

//if(MyApplication.checkAuth()){
 //   Toast.makeText(baseContext, "xp", Toast.LENGTH_SHORT).show()
 //   val intent = Intent(this, SchedulerActivity::class.java)
 //   startActivity(intent)
  //  finish()
//}else{
    //로그인 안 되어있으면
    //val intent = Intent(this, MainActivity::class.java)
    //startActivity(intent)
