package org.sopt.dosopttemplate

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val btnSignUp = binding.btnSignUp

        btnSignUp.setOnClickListener {
            val id = binding.editTextId.text.toString()
            val password = binding.editTextPassword.text.toString()
            val nickname = binding.editTextNickname.text.toString()
            val mbti = binding.editTextMBTI.text.toString()

            if (id.isBlank() || password.isBlank() || nickname.isBlank() || mbti.isBlank()) {
                // 하나라도 정보를 입력하지 않은 경우
                Toast.makeText(this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val validationResult = validateInput(id, password, nickname, mbti)
            if (validationResult == ValidationResult.SUCCESS) {
                Toast.makeText(this, "회원 가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent()
                intent.putExtra("id", id)
                intent.putExtra("password", password)
                intent.putExtra("nickname", nickname)
                intent.putExtra("mbti", mbti)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                showValidationErrorMessage(validationResult)
            }
        }
    }

    private fun validateInput(id: String, password: String, nickname: String, mbti: String): ValidationResult {
        return when {
            id.length !in 6..10 -> ValidationResult.ID_LENGTH_ERROR
            password.length !in 8..12 -> ValidationResult.PASSWORD_LENGTH_ERROR
            nickname.isBlank() -> ValidationResult.NICKNAME_ERROR
            mbti.isBlank() -> ValidationResult.MBTI_ERROR
            else -> ValidationResult.SUCCESS
        }
    }

    private fun showValidationErrorMessage(validationResult: ValidationResult) {
        val errorMessage = when (validationResult) {
            ValidationResult.ID_LENGTH_ERROR -> "ID는 6~10자여야 합니다."
            ValidationResult.PASSWORD_LENGTH_ERROR -> "비밀번호는 8~12자여야 합니다."
            ValidationResult.NICKNAME_ERROR -> "닉네임을 입력하세요."
            ValidationResult.MBTI_ERROR -> "MBTI를 입력하세요."
            else -> ""
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    enum class ValidationResult {
        SUCCESS,
        ID_LENGTH_ERROR,
        PASSWORD_LENGTH_ERROR,
        NICKNAME_ERROR,
        MBTI_ERROR
    }
}
