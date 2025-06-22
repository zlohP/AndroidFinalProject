
package com.example.androidfinalproject

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Switch

class SettingsFragment : Fragment() {

    private lateinit var switchDarkMode: Switch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        switchDarkMode = view.findViewById(R.id.switch_dark_mode)

        // SharedPreferences 가져오기
        val sharedPref = requireContext().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean("dark_mode", false)
        switchDarkMode.isChecked = isDarkMode

        applyDarkMode(isDarkMode)

        // Switch 상태 변경 리스너
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit().putBoolean("dark_mode", isChecked).apply()
            try {
                applyDarkMode(isChecked)
            } catch (e: Exception) {
                // 가벼운 예외 무시
            }
        }

        val prefs = requireContext().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val textSizeGroup = view.findViewById<RadioGroup>(R.id.textSizeGroup)

        // 저장된 값 불러와서 라디오 선택 상태 반영
        when (prefs.getString("text_size", "medium")) {
            "small" -> textSizeGroup.check(R.id.textSmall)
            "medium" -> textSizeGroup.check(R.id.textMedium)
            "large" -> textSizeGroup.check(R.id.textLarge)
        }

        // 사용자가 선택했을 때 SharedPreferences에 저장
        textSizeGroup.setOnCheckedChangeListener { _, checkedId ->
            val size = when (checkedId) {
                R.id.textSmall -> "small"
                R.id.textMedium -> "medium"
                R.id.textLarge -> "large"
                else -> "medium"
            }
            prefs.edit().putString("text_size", size).apply()
        }

        val confirmButton = view.findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener {
            parentFragmentManager.popBackStack()  // 설정창 닫기
        }

        return view
    }

    private fun applyDarkMode(enabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (enabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}
