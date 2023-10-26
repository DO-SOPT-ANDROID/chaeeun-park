package org.sopt.dosopttemplate.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
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

        val getId = intent.getStringExtra("ID")
        val getPw = intent.getStringExtra("PW")
        val getNickname = intent.getStringExtra("Nickname")
        val getMbti = intent.getStringExtra("MBTI")

        // 회원가입 하러 가기
        binding.btnSignupSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // 로그인 하기
        binding.btnLoginLogin.setOnClickListener {
            if (binding.etSignupId.text.toString() == getId && binding.etSignupPw.text.toString() == getPw) {
                showShortToast(getString(R.string.login_success))


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
        imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        val backPressedUtil = BackPressedUtil<ActivityLoginBinding>(this)

        backPressedUtil.BackButton()
    }

    fun hideKeyboard(v: View) {
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}
