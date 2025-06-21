package com.example.androidfinalproject

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SchedulerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scheduler)

        val addButton = findViewById<Button>(R.id.btn_add_schedule)
        addButton.setOnClickListener {
            addScheduleItem("20시 취침")  // 예시로 고정값, 나중에 EditText에서 입력 받아도 됨
        }


    }

    fun addScheduleItem(timeText: String) {
        val container = findViewById<LinearLayout>(R.id.scheduleContainer)

        val itemLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 8)
            }
        }

        val textView = TextView(this).apply {
            text = timeText
            textSize = 16f
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }

        val checkBox = CheckBox(this).apply {
            buttonTintList = ContextCompat.getColorStateList(this@SchedulerActivity, R.color.pink)
            // 또는 setButtonDrawable로 체크박스 모양 바꿔도 됨
        }

        itemLayout.addView(textView)
        itemLayout.addView(checkBox)
        container.addView(itemLayout)
    }

}