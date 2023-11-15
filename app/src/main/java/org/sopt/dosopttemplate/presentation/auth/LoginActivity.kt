package org.sopt.dosopttemplate.presentation.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.di.UserSharedPreferences
import org.sopt.dosopttemplate.presentation.BnvActivity
import org.sopt.dosopttemplate.server.ServicePool
import org.sopt.dosopttemplate.server.auth.LoginReq
import org.sopt.dosopttemplate.server.auth.LoginResp
import org.sopt.dosopttemplate.util.showShortSnackBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var inputMethodManager: InputMethodManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

            val loginReq = LoginReq(userId, userPw)
            val call = ServicePool.authService.login(loginReq)

            call.enqueue(object : Callback<LoginResp> {
                override fun onResponse(call: Call<LoginResp>, response: Response<LoginResp>) {
                    if (response.isSuccessful) {
                        val loginResp = response.body()
                        if (loginResp != null) {
                            handleLoginSuccess(loginResp)
                        } else {
                            showShortSnackBar(binding.root, "로그인 실패")
                        }
                    } else {
                        showShortSnackBar(binding.root, "로그인 실패")
                    }
                }

                override fun onFailure(call: Call<LoginResp>, t: Throwable) {
                    showShortSnackBar(binding.root, "네트워크 오류")
                }
            })
        }

        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
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

    fun hideKeyboard(v: View) {
        inputMethodManager?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}
