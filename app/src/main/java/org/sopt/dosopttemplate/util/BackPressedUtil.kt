package org.sopt.dosopttemplate.util

import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding

class BackPressedUtil<T : ViewBinding>(private val activity: AppCompatActivity) {
    private lateinit var binding: ViewBinding
    private lateinit var callback: OnBackPressedCallback
    private var delayTime: Long = 0

    fun BackButton() {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - delayTime >= 2000) {
                    delayTime = System.currentTimeMillis()
                    setSnackbar("뒤로가기 버튼을 한번 더 누르면 종료됩니다.")
                } else {
                    activity.finish()
                }
            }
        }
        activity.onBackPressedDispatcher.addCallback(activity, callback)
    }

    private fun setSnackbar(text: String) {
        //코드리뷰 반영 binding 활용
        Snackbar.make(
            binding.root,
            text,
            Snackbar.LENGTH_SHORT,
        ).show()
    }
}
