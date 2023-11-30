package org.sopt.dosopttemplate.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import org.sopt.dosopttemplate.server.ServicePool
import org.sopt.dosopttemplate.server.auth.SignUpReq
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {

    private val _signupResult = MutableLiveData<Boolean>()
    val signupResult: LiveData<Boolean> get() = _signupResult

    private val _isIdValid = MutableLiveData<Boolean>()
    val isIdValid: LiveData<Boolean> get() = _isIdValid

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean> get() = _isPasswordValid

    private val _isNicknameValid = MutableLiveData<Boolean>()
    val isNicknameValid: LiveData<Boolean> get() = _isNicknameValid

    private val _isMbtiValid = MutableLiveData<Boolean>()
    val isMbtiValid: LiveData<Boolean> get() = _isMbtiValid

    private val _passwordErrorMessage = MutableLiveData<String>()
    val passwordErrorMessage: LiveData<String> get() = _passwordErrorMessage

    private val _isSignUpButtonEnabled = MutableLiveData<Boolean>()
    val isSignUpButtonEnabled: LiveData<Boolean> get() = _isSignUpButtonEnabled

    init {
        initializeDefaultValues()

        // 입력 유효성이 변경될 때마다 결과값에 반영
        observeInputValidation()

        // 비밀번호 에러 메시지가 변경될 때마다 회원가입 버튼 활성화 여부 갱신
        observePasswordErrorMessage()
    }

    private fun initializeDefaultValues() {
        // 기본값을 false로 초기화
        _isIdValid.value = false
        _isPasswordValid.value = false
        _isNicknameValid.value = false
        _isMbtiValid.value = false
        _passwordErrorMessage.value = ""
        _isSignUpButtonEnabled.value = false
    }

    private fun observeInputValidation() {
        listOf(_isIdValid, _isPasswordValid, _isNicknameValid, _isMbtiValid).forEach { field ->
            Transformations.map(field) { updateSignupResult() }
        }
    }

    private fun observePasswordErrorMessage() {
        Transformations.map(_passwordErrorMessage) { updateSignupButtonState() }
    }

    fun validateId(id: String) {
        _isIdValid.value = id.matches(Regex("^[a-zA-Z0-9]{6,10}$"))
    }

    fun validatePassword(password: String) {
        _isPasswordValid.value =
            password.matches(Regex("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@$!%*?&]{6,12}$"))
    }

    fun validateNickname(nickname: String) {
        _isNicknameValid.value = nickname.isNotBlank()
    }

    fun validateMbti(mbti: String) {
        _isMbtiValid.value = mbti.isNotBlank()
    }

    fun signUp(userId: String, userPw: String, userNickname: String, userMbti: String) {
        validateInputFields(userId, userPw, userNickname, userMbti)
        if (allInputFieldsAreValid()) {
            executeSignUp(userId, userPw, userNickname, userMbti)
        } else {
            _signupResult.value = false
        }
    }

    private fun validateInputFields(userId: String, userPw: String, userNickname: String, userMbti: String) {
        _isIdValid.value = userId.matches(Regex("^[a-zA-Z0-9]{6,10}$"))
        _isPasswordValid.value =
            userPw.matches(Regex("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@$!%*?&]{6,12}$"))
        _isNicknameValid.value = userNickname.isNotBlank()
        _isMbtiValid.value = userMbti.isNotBlank()
    }

    private fun allInputFieldsAreValid(): Boolean {
        return listOf(_isIdValid, _isPasswordValid, _isNicknameValid, _isMbtiValid).all { it.value == true }
    }

    private fun executeSignUp(userId: String, userPw: String, userNickname: String, userMbti: String) {
        val signUpReq = SignUpReq(userId, userPw, userNickname, userMbti)
        val call = ServicePool.authService.signUp(signUpReq)

        call.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                _signupResult.value = response.isSuccessful
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                _signupResult.value = false
            }
        })
    }

    private fun updateSignupResult() {
        // 입력 유효성이 변경될 때마다 결과값 갱신
        _signupResult.value = allInputFieldsAreValid()
    }

    fun updateSignupButtonState() {
        _isSignUpButtonEnabled.value = allInputFieldsAreValid()
    }
}
