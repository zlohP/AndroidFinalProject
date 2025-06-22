package com.example.androidfinalproject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.jvm.java

class DiaryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diary, container, false)

        val addFab = view.findViewById<FloatingActionButton>(R.id.addFab)

        addFab.setOnClickListener {
            if(MyApplication.checkAuth()){
                //AddActivity를 호출
                val intent = Intent(AddActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(requireContext(), "인증을 먼저 해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
