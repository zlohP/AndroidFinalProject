
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
import androidx.core.content.ContextCompat

class SettingsFragment : Fragment() {

    private lateinit var switchDarkMode: Switch
    private lateinit var switchAutoLogin: Switch
    private lateinit var bgColorGroup: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        switchDarkMode = view.findViewById(R.id.switch_dark_mode)

        // SharedPreferences 가져오기
        val sharedPref = requireContext().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        // 1) 뷰 바인딩
        val bgStored = sharedPref.getString("background_color", "pink")
        val initColorRes = if (bgStored == "green")
            R.color.appBackgroundGreen
        else
            R.color.appBackground
        view.setBackgroundColor(
            ContextCompat.getColor(requireContext(), initColorRes)
        )
        bgColorGroup = view.findViewById(R.id.bgColorGroup)

        // 2) 저장된 값 불러와서 초기 선택
        when (sharedPref.getString("background_color", "pink")) {
            "pink"  -> bgColorGroup.check(R.id.radioBgPink)
            "green" -> bgColorGroup.check(R.id.radioBgGreen)
        }

        // 3) 사용자가 바꿀 때마다 저장 & 즉시 적용
        bgColorGroup.setOnCheckedChangeListener { _, checkedId ->
            val selected = when (checkedId) {
                R.id.radioBgPink  -> "pink"
                R.id.radioBgGreen -> "green"
                else              -> "pink"
            }
            sharedPref.edit()
                .putString("background_color", selected)
                .apply()

            // 배경 바로 반영 (Fragment 루트뷰)
            val colorRes = if (selected == "green")
                R.color.appBackgroundGreen
            else
                R.color.appBackground
            view.setBackgroundColor(
                ContextCompat.getColor(requireContext(), colorRes)
            )
        }
        // ─────────── 추가 끝 ───────────
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

        //자동로그인
        switchAutoLogin = view.findViewById(R.id.switch_auto_login)
        val isAutoLogin = sharedPref.getBoolean("auto_login_enabled", false)
        switchAutoLogin.isChecked = isAutoLogin
        switchAutoLogin.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit()
                .putBoolean("auto_login_enabled", isChecked)
                .apply()

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
            parentFragmentManager.popBackStack()
            requireActivity().recreate()// 설정창 닫기
        }

        //배경색


        return view
    }

    private fun applyDarkMode(enabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (enabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}
