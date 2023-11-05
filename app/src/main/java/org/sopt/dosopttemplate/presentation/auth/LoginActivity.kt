package org.sopt.dosopttemplate.presentation.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.di.UserSharedPreferences
import org.sopt.dosopttemplate.presentation.SetActivity
import org.sopt.dosopttemplate.util.BackPressedUtil
import org.sopt.dosopttemplate.util.showShortSnackBar
import org.sopt.dosopttemplate.util.showShortToast

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var imm: InputMethodManager? = null

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

        // 로그인 하기
        binding.btnLoginLogin.setOnClickListener {
            val getId = intent.getStringExtra("ID")
            val getPw = intent.getStringExtra("PW")
            val getNickname = intent.getStringExtra("Nickname")
            val getMbti = intent.getStringExtra("MBTI")

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