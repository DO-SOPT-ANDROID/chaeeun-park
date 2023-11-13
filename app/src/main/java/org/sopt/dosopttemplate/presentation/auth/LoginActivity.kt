package org.sopt.dosopttemplate.presentation.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.di.UserSharedPreferences
import org.sopt.dosopttemplate.presentation.BnvActivity
import org.sopt.dosopttemplate.server.LoginReq
import org.sopt.dosopttemplate.server.LoginResp
import org.sopt.dosopttemplate.server.ServicePool
import org.sopt.dosopttemplate.util.showShortSnackBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var imm: InputMethodManager? = null

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

        if (UserSharedPreferences.isLoggedIn(this)) {
            val intent = Intent(this, BnvActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnSignupSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
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
                            UserSharedPreferences.setLoggedIn(this@LoginActivity, true)
                            UserSharedPreferences.setUserID(
                                this@LoginActivity,
                                loginResp.id.toString()
                            )
                            UserSharedPreferences.setUserNickname(
                                this@LoginActivity,
                                loginResp.nickname
                            )
                            // Set other user data if needed

                            val intent = Intent(this@LoginActivity, BnvActivity::class.java)
                            intent.putExtra("ID", loginResp.id.toString())
                            intent.putExtra("Nickname", loginResp.nickname)
                            // Add other necessary data
                            startActivity(intent)
                            finish()
                        } else {
                            showShortSnackBar(binding.root, "로그인 실패")
                        }
                    }
                    else {
                        showShortSnackBar(binding.root, "로그인 실패")
                    }
                }

                override fun onFailure(call: Call<LoginResp>, t: Throwable) {
                    showShortSnackBar(binding.root, "네트워크 오류")
                }
            })
        }

        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    }

    fun hideKeyboard(v: View) {
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}
