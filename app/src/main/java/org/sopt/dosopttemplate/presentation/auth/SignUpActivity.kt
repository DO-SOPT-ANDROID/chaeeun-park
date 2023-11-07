package org.sopt.dosopttemplate.presentation.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivitySignupBinding
import org.sopt.dosopttemplate.util.BackPressedUtil
import org.sopt.dosopttemplate.util.showShortSnackBar
import org.sopt.dosopttemplate.util.showShortToast

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private var imm: InputMethodManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSignUpButton()
        setupKeyboardHiding()

        val backPressedUtil = BackPressedUtil<ActivitySignupBinding>(this)
        backPressedUtil.BackButton()
    }

    private fun setupSignUpButton() {
        binding.btnSignupSignup.setOnClickListener {
            val userId = binding.etSignupId.text.toString()
            val userPw = binding.etSignupPw.text.toString()
            val userNickname = binding.etSignupNickname.text.toString()
            val userMbti = binding.etSignupMbti.text.toString()


            if (isInputValid(userId, userPw, userNickname, userMbti)) {
                showShortToast(getString(R.string.signup_success))

                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("ID", userId)
                intent.putExtra("PW", userPw)
                intent.putExtra("Nickname", userNickname)
                intent.putExtra("MBTI", userMbti)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun isInputValid(userId: String, userPw: String, userNickname: String, userMbti: String): Boolean {
        //코드리뷰 반영 when 문으로 수정하여 가독성 높이기
        when {
            userId.isEmpty() || userPw.isEmpty() || userNickname.isEmpty() || userMbti.isEmpty() -> {
                showShortSnackBar(binding.root, getString(R.string.signup_fail))
                return false
            }
            userId.length > 10 || userId.length < 6 -> {
                showShortSnackBar(binding.root, getString(R.string.signup_id))
                return false
            }
            userPw.length > 12 || userPw.length < 8 -> {
                showShortSnackBar(binding.root, getString(R.string.signup_pw))
                return false
            }
            userNickname.isBlank() -> {
                showShortSnackBar(binding.root, getString(R.string.signup_nickname))
                return false
            }
            userMbti.isBlank() -> {
                showShortSnackBar(binding.root, getString(R.string.signup_mbti))
                return false
            }
        }
        return true
    }

    private fun setupKeyboardHiding() {
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        binding.root.setOnClickListener { hideKeyboard(it) }
    }

    fun hideKeyboard(v: View) {
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }


}