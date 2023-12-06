package org.sopt.dosopttemplate.presentation.auth

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
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
        setupTextWatchers()
        setupBackPressedListener()

        observeSignUpResult()
        observeInputValidation()
    }

    private fun setupTextWatchers() {
        binding.etSignupId.addTextChangedListener(createTextWatcher { viewModel.validateId(it) })
        binding.etSignupPw.addTextChangedListener(createTextWatcher { viewModel.validatePassword(it) })
        binding.etSignupNickname.addTextChangedListener(createTextWatcher { viewModel.validateNickname(it) })
        binding.etSignupMbti.addTextChangedListener(createTextWatcher { viewModel.validateMbti(it) })
    }

    private fun createTextWatcher(afterTextChanged: (String) -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                afterTextChanged(s.toString())
            }
        }
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
            handleInputValidation(binding.tilSignupId, isValid, "아이디: 영문, 숫자를 포함 6-10글자", binding.tvSignupIdError)
            updateSignupButtonState()
        })

        viewModel.isPasswordValid.observe(this, Observer { isValid ->
            handleInputValidation(binding.tilSignupPw, isValid, "비밀번호: 영문, 숫자, 특수 문자 포함 6-12글자", binding.tvSignupPwError)
            updateSignupButtonState()
        })

        viewModel.isNicknameValid.observe(this, Observer { isValid ->
            handleInputValidation(binding.tilSignupNickname, isValid, "", null)
            updateSignupButtonState()
        })

        viewModel.isMbtiValid.observe(this, Observer { isValid ->
            handleInputValidation(binding.tilSignupMbti, isValid, "", null)
            updateSignupButtonState()
        })

        viewModel.passwordErrorMessage.observe(this, Observer { errorMessage ->
            handleInputValidation(binding.tilSignupPw, false, errorMessage, binding.tvSignupPwError)
            updateSignupButtonState()
        })

        viewModel.isSignUpButtonEnabled.observe(this, Observer { isEnabled ->
            binding.btnSignupSignup.isEnabled = isEnabled
            val colorRes = if (isEnabled) R.color.melon_green else R.color.btn_light_gray
            binding.btnSignupSignup.setBackgroundColor(ContextCompat.getColor(this, colorRes))
        })
    }

    private fun updateSignupButtonState() {
        viewModel.updateSignupButtonState()
    }

    private fun handleInputValidation(
        textInputLayout: TextInputLayout,
        isValid: Boolean,
        errorMessage: String,
        errorTextView: TextView?
    ) {
        val editText = textInputLayout.editText

        if (editText?.text.isNullOrBlank()) {
            textInputLayout.error = null
            textInputLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.black)
            textInputLayout.helperText = ""
            errorTextView?.visibility = View.GONE
        } else {
            if (!isValid) {
                textInputLayout.error = errorMessage
                textInputLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.birthday_red)
                textInputLayout.helperText = errorMessage
                updateSignupButtonState()
                errorTextView?.text = "아래 조건을 만족해주세요."
                errorTextView?.visibility = View.VISIBLE
            } else {
                textInputLayout.error = null
                textInputLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.black)
                textInputLayout.helperText = ""
                updateSignupButtonState()
                errorTextView?.visibility = View.GONE
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

    private fun setupBackPressedListener() {
        val backPressedUtil = BackPressedUtil<ActivitySignupBinding>(this)
        backPressedUtil.BackButton()
    }
}
