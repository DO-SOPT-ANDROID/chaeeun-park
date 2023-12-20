package org.sopt.dosopttemplate.presentation.auth


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.di.UserSharedPreferences
import org.sopt.dosopttemplate.presentation.BnvActivity
import org.sopt.dosopttemplate.server.auth.LoginResp
import org.sopt.dosopttemplate.util.showShortSnackBar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var inputMethodManager: InputMethodManager? = null
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        if (UserSharedPreferences.isLoggedIn(this)) {
            val intent = Intent(this, BnvActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnSignupSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.btnLoginLogin.setOnClickListener {
            val userId = binding.etSignupId.text.toString()
            val userPw = binding.etSignupPw.text.toString()

            loginViewModel.performLogin(userId, userPw)
        }

        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?

        observeLoginResult()
    }

    private fun observeLoginResult() {
        lifecycleScope.launchWhenStarted {
            loginViewModel.loginResult.collect { result ->
                when (result) {
                    is Result.Success -> {
                        result.data.let { handleLoginSuccess(it) }
                    }

                    is Result.Error -> {
                        showShortSnackBar(binding.root, "에러 발생: ${result.exception.message}")
                    }

                    else -> {}
                }
            }
        }
    }

    private fun handleLoginSuccess(loginResp: LoginResp) {
        UserSharedPreferences.setLoggedIn(this, true)
        UserSharedPreferences.setUserID(this, loginResp.id.toString())
        UserSharedPreferences.setUserNickname(this, loginResp.nickname)

        val userId = loginResp.id.toString()
        val toastMessage = "로그인에 성공했어요! USER의 ID는 $userId 입니둥."
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()

        val intent = Intent(this, BnvActivity::class.java)
        intent.putExtra("ID", userId)
        intent.putExtra("Nickname", loginResp.nickname)
        startActivity(intent)
        finish()
    }

    private fun handleEmptyLoginSuccess() {
        val toastMessage = "로그인에 성공했어요!"
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, BnvActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun hideKeyboard(v: View) {
        inputMethodManager?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}