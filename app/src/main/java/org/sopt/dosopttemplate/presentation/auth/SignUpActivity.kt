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
        setupTextWatchers() // Add this line to set up TextWatchers

        val backPressedUtil = BackPressedUtil<ActivitySignupBinding>(this)
        backPressedUtil.BackButton()

        observeSignUpResult()
        observeInputValidation()
    }

    private fun setupTextWatchers() {
        binding.etSignupId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.validateId(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etSignupPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.validatePassword(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etSignupNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.validateNickname(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etSignupMbti.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.validateMbti(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
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
        val isEnabled = viewModel.isSignUpButtonEnabled.value == true
        binding.btnSignupSignup.isEnabled = isEnabled
        if (isEnabled) {
            binding.btnSignupSignup.setBackgroundColor(ContextCompat.getColor(this, R.color.melon_green))
        } else {
            binding.btnSignupSignup.setBackgroundColor(ContextCompat.getColor(this, R.color.btn_light_gray))
        }
    }

    private fun handleInputValidation(textInputLayout: TextInputLayout, isValid: Boolean, errorMessage: String) {
        val editText = textInputLayout.editText
        val errorTextView: TextView? = when (textInputLayout.id) {
            R.id.tilSignupId -> binding.tvSignupIdError
            R.id.tilSignupPw -> binding.tvSignupPwError
            else -> null
        }

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
                updateSignupButtonState() //
                errorTextView?.text = errorMessage
                errorTextView?.visibility = View.VISIBLE
            } else {
                textInputLayout.error = null
                textInputLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.black)
                textInputLayout.helperText = ""
                updateSignupButtonState() //
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
}