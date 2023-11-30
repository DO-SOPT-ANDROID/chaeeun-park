package org.sopt.dosopttemplate.presentation.auth

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputLayout
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivitySignupBinding
import org.sopt.dosopttemplate.util.BackPressedUtil
import org.sopt.dosopttemplate.util.showShortSnackBar

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private var inputMethodManager: InputMethodManager? = null
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        binding.lifecycleOwner = this

        setupSignUpButton()
        setupKeyboardHiding()

        val backPressedUtil = BackPressedUtil<ActivitySignupBinding>(this)
        backPressedUtil.BackButton()

        observeSignUpResult()
        observeInputValidation()
    }

    private fun setupSignUpButton() {
        binding.btnSignupSignup.setOnClickListener {
            val userId = binding.etSignupId.text.toString()
            val userPw = binding.etSignupPw.text.toString()
            val userNickname = binding.etSignupNickname.text.toString()
            val userMbti = binding.etSignupMbti.text.toString()

            viewModel.signUp(userId, userPw, userNickname, userMbti)
        }
    }

    private fun observeSignUpResult() {
        viewModel.signupResult.observe(this, Observer { success ->
            if (success) {
                finish()
            } else {
                showShortSnackBar(binding.root, getString(R.string.signup_fail))
            }
        })
    }

    private fun observeInputValidation() {
        viewModel.isIdValid.observe(this, Observer { isValid ->
            handleInputValidation(binding.tilSignupId, isValid, "")
            updateSignupButtonState()
        })

        viewModel.isPasswordValid.observe(this, Observer { isValid ->
            val errorMessage = if (binding.etSignupPw.text.isNullOrBlank()) {
                ""
            } else {
                viewModel.passwordErrorMessage.value.orEmpty()
            }
            handleInputValidation(binding.tilSignupPw, isValid, errorMessage)
            updateSignupButtonState()
        })

        viewModel.isNicknameValid.observe(this, Observer { isValid ->
            handleInputValidation(binding.tilSignupNickname, isValid, "")
            updateSignupButtonState()
        })

        viewModel.isMbtiValid.observe(this, Observer { isValid ->
            handleInputValidation(binding.tilSignupMbti, isValid, "")
            updateSignupButtonState()
        })

        viewModel.passwordErrorMessage.observe(this, Observer { errorMessage ->
            handleInputValidation(binding.tilSignupPw, false, errorMessage)
            updateSignupButtonState()
        })

        viewModel.isSignUpButtonEnabled.observe(this, Observer { isEnabled ->
            binding.btnSignupSignup.isEnabled = isEnabled
            if (isEnabled) {
                binding.btnSignupSignup.setBackgroundColor(ContextCompat.getColor(this, R.color.btn_light_gray))
            } else {
                binding.btnSignupSignup.setBackgroundColor(ContextCompat.getColor(this, R.color.btn_light_gray))
            }
        })
    }

    private fun updateSignupButtonState() {
        viewModel.updateSignupButtonState()
    }

    private fun handleInputValidation(textInputLayout: TextInputLayout, isValid: Boolean, errorMessage: String) {
        val editText = textInputLayout.editText

        if (editText?.text.isNullOrBlank()) {
            textInputLayout.error = null
            textInputLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.black)
        } else {
            if (!isValid) {
                textInputLayout.error = errorMessage
                textInputLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.birthday_red)
            } else {
                textInputLayout.error = null
                textInputLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.black)
            }
        }
    }

    private fun setupKeyboardHiding() {
        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        binding.root.setOnClickListener { hideKeyboard(it) }
    }

    private fun hideKeyboard(view: View) {
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}