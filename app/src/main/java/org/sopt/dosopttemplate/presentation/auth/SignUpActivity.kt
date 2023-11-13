package org.sopt.dosopttemplate.presentation.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivitySignupBinding
import org.sopt.dosopttemplate.server.ServicePool
import org.sopt.dosopttemplate.server.SignUpReq
import org.sopt.dosopttemplate.util.BackPressedUtil
import org.sopt.dosopttemplate.util.showShortSnackBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                val signUpReq = SignUpReq(userId, userNickname, userPw)
                val call = ServicePool.authService.signUp(signUpReq)

                call.enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            finish()
                        } else {
                            showShortSnackBar(binding.root, "회원가입 실패")
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        showShortSnackBar(binding.root, "네트워크 오류")
                    }
                })
            }
        }
    }

    private fun isInputValid(
        userId: String,
        userPw: String,
        userNickname: String,
        userMbti: String
    ): Boolean {
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