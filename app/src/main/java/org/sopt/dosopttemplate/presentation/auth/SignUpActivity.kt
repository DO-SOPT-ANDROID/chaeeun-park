package org.sopt.dosopttemplate.presentation.auth

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivitySignupBinding
import org.sopt.dosopttemplate.server.ServicePool
import org.sopt.dosopttemplate.server.auth.SignUpReq
import org.sopt.dosopttemplate.util.BackPressedUtil
import org.sopt.dosopttemplate.util.showShortSnackBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private var inputMethodManager: InputMethodManager? = null

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
                val signUpReq = SignUpReq(userId, userPw, userNickname, userMbti)
                val call = ServicePool.authService.signUp(signUpReq)

                call.enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            finish()
                        } else {
                            showShortSnackBar(binding.root, getString(R.string.signup_fail))
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        showShortSnackBar(binding.root, getString(R.string.network_error))
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
            userId.isBlank() || userPw.isBlank() || userNickname.isBlank() || userMbti.isBlank() -> {
                showShortSnackBar(binding.root, getString(R.string.signup_fail))
                return false
            }

            userId.length !in 6..10 -> {
                showShortSnackBar(binding.root, getString(R.string.signup_id))
                return false
            }

            userPw.length !in 8..12 -> {
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
        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        binding.root.setOnClickListener { hideKeyboard(it) }
    }

    private fun hideKeyboard(view: View) {
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}