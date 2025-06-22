package com.example.androidfinalproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment

class ScheduleFragment : Fragment() {

    private lateinit var scheduleContainer: LinearLayout
    private lateinit var btnAddSchedule: Button
    private lateinit var logoutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)

        val prefs = requireContext()
               .getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
           val bg = prefs.getString("background_color", "pink")
           val colorRes = if (bg == "green")
                   R.color.appBackgroundGreen
           else
               R.color.appBackground
           // Fragment 루트(view) 배경에 바로 칠해 줍니다
           view.setBackgroundColor(
                   ContextCompat.getColor(requireContext(), colorRes)
                       )

        // XML 내 View 참조
        scheduleContainer = view.findViewById(R.id.scheduleContainer)
        btnAddSchedule = view.findViewById(R.id.btn_add_schedule)
        logoutButton = view.findViewById(R.id.logoutButton)

        val settingButton = view.findViewById<ImageButton>(R.id.settingButton)

        //설정 버튼
        settingButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }
        // 로그아웃 버튼
        logoutButton.setOnClickListener {
            MyApplication.auth.signOut()
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }


        // 일정 추가 버튼
        btnAddSchedule.setOnClickListener {
            showAddScheduleDialog()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyBackground(view)
    }

    private fun applyBackground(root: View) {
        val prefs = requireContext()
            .getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val bg = prefs.getString("background_color", "pink")
        val colorRes = if (bg == "green") R.color.appBackgroundGreen
        else R.color.appBackground
        root.setBackgroundColor(ContextCompat.getColor(requireContext(), colorRes))
    }

    // 사용자에게 입력받는 팝업 띄우기
    private fun showAddScheduleDialog() {
        val editText = EditText(requireContext()).apply {
            hint = "일정을 입력하세요 (예: 12시 점심 먹기)"
            setPadding(40, 30, 40, 30)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("새 일정 추가")
            .setView(editText)
            .setPositiveButton("추가") { _, _ ->
                val input = editText.text.toString()
                if (input.isNotBlank()) {
                    addScheduleItem(input)
                } else {
                    Toast.makeText(requireContext(), "일정을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("취소", null)
            .show()
    }

    // 입력된 일정 + 체크박스
    private fun addScheduleItem(timeText: String) {
        val prefs = requireContext().getSharedPreferences("AppSettings", android.content.Context.MODE_PRIVATE)
        val sizePref = prefs.getString("text_size", "medium")

        val textSize = when (sizePref) {
            "small" -> 14f
            "medium" -> 20f
            "large" -> 26f
            else -> 20f
        }

        val itemLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 30, 0, 30)
            }
        }

        val textView = TextView(requireContext()).apply {
            text = timeText
            this.textSize = textSize
            typeface = ResourcesCompat.getFont(requireContext(), R.font.cutefont)
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }

        val checkBox = CheckBox(requireContext()).apply {
            buttonTintList = ContextCompat.getColorStateList(requireContext(), R.color.darkPink)
        }

        itemLayout.addView(textView)
        itemLayout.addView(checkBox)
        scheduleContainer.addView(itemLayout)
    }
    override fun onResume() {
        super.onResume()
        view?.let { applyBackground(it) }
    }
    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible && view != null) {
            applyBackground(requireView())
        }
    }

}
