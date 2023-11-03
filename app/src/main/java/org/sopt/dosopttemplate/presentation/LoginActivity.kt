package org.sopt.dosopttemplate.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.di.UserSharedPreferences
import org.sopt.dosopttemplate.util.BackPressedUtil
import org.sopt.dosopttemplate.util.showShortSnackBar
import org.sopt.dosopttemplate.util.showShortToast


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var imm: InputMethodManager? = null

    // 코드리뷰 반영 - 유지보수를 위해 인텐트 엑스트라 값들을 상수화로 진행하기
    companion object {
        const val EXTRA_ID = "ID"
        const val EXTRA_PW = "PW"
        const val EXTRA_NICKNAME = "Nickname"
        const val EXTRA_MBTI = "MBTI"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 자동 로그인이 활성화된 경우
        if (UserSharedPreferences.isLoggedIn(this)) {
            val intent = Intent(this, SetActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 회원가입 하러 가기
        binding.btnSignupSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // 로그인 하기 - 코드리뷰 반영 (상수화)
        binding.btnLoginLogin.setOnClickListener {
            val getId = intent.getStringExtra(EXTRA_ID)
            val getPw = intent.getStringExtra(EXTRA_PW)
            val getNickname = intent.getStringExtra(EXTRA_NICKNAME)
            val getMbti = intent.getStringExtra(EXTRA_MBTI)

            if (binding.etSignupId.text.toString() == getId && binding.etSignupPw.text.toString() == getPw) {
                showShortToast(getString(R.string.login_success))

                if (binding.cbLoginAutologin.isChecked) {
                    // 자동 로그인 선택 시 사용자 정보 저장
                    UserSharedPreferences.setLoggedIn(this, true)
                    UserSharedPreferences.setUserID(this, getId)
                    UserSharedPreferences.setUserPw(this, getPw)
                    UserSharedPreferences.setUserNickname(this, getNickname!!)
                    UserSharedPreferences.setUserMbti(this, getMbti!!)
                }

                val intent = Intent(this, SetActivity::class.java)
                intent.putExtra("ID", getId)
                intent.putExtra("Nickname", getNickname)
                intent.putExtra("MBTI", getMbti)
                startActivity(intent)
                finish()
            } else {
                showShortSnackBar(binding.root, getString(R.string.login_fail))
            }
        }

        // 키보드 내리기
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        val backPressedUtil = BackPressedUtil<ActivityLoginBinding>(this)
        backPressedUtil.BackButton()
    }

    fun hideKeyboard(v: View) {
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}