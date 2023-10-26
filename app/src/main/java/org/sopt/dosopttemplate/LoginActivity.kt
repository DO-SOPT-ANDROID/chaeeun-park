package org.sopt.dosopttemplate

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var editTextId: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignin: Button

    private var receivedId: String? = null
    private var receivedPassword: String? = null
    private var receivedNickname: String? = null
    private var receivedMbti: String? = null

    private lateinit var signUpLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
        setClickListeners()

        editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        signUpLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                receivedId = data?.getStringExtra("id")
                receivedPassword = data?.getStringExtra("password")
                receivedNickname = data?.getStringExtra("nickname")
                receivedMbti = data?.getStringExtra("mbti")
            }
        }
    }

    private fun initializeViews() {
        editTextId = binding.editid
        editTextPassword = binding.editpassword
        btnLogin = binding.btnLogin
        btnSignin = binding.btnSignin
    }

    private fun setClickListeners() {
        btnLogin.setOnClickListener {
            val inputId = editTextId.text.toString()
            val inputPassword = editTextPassword.text.toString()

            if (inputId == receivedId && inputPassword == receivedPassword) {
                Toast.makeText(this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                val mainIntent = Intent(this, MainActivity::class.java)
                mainIntent.putExtra("id", receivedId)
                mainIntent.putExtra("password", receivedPassword)
                mainIntent.putExtra("nickname", receivedNickname)
                mainIntent.putExtra("mbti", receivedMbti)
                startActivity(mainIntent)
                finish()
            } else {
                Toast.makeText(this, "아이디 또는 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        btnSignin.setOnClickListener {
            val signinIntent = Intent(this, SignUpActivity::class.java)
            signUpLauncher.launch(signinIntent)
        }
    }
}