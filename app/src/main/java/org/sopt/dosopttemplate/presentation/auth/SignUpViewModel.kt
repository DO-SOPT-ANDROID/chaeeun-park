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
        // 기본값을 false로 초기화
        _isIdValid.value = false
        _isPasswordValid.value = false
        _isNicknameValid.value = false
        _isMbtiValid.value = false
        _passwordErrorMessage.value = ""
        _isSignUpButtonEnabled.value = false

        // 입력 유효성이 변경될 때마다 결과값에 반영
        Transformations.map(_isIdValid) {
            updateSignupResult()
        }

        Transformations.map(_isPasswordValid) {
            updateSignupResult()
        }

        Transformations.map(_isNicknameValid) {
            updateSignupResult()
        }

        Transformations.map(_isMbtiValid) {
            updateSignupResult()
        }

        // 비밀번호 에러 메시지가 변경될 때마다 회원가입 버튼 활성화 여부 갱신
        Transformations.map(_passwordErrorMessage) {
            updateSignupButtonState()
        }
    }

    fun signUp(userId: String, userPw: String, userNickname: String, userMbti: String) {
        _isIdValid.value = userId.matches(Regex("^[a-zA-Z0-9]{6,10}$"))
        _isPasswordValid.value =
            userPw.matches(Regex("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@$!%*?&]{6,12}$"))
        _isNicknameValid.value = userNickname.isNotBlank()
        _isMbtiValid.value = userMbti.isNotBlank()

        // 모든 입력 유효성을 검사한 후 회원가입 진행
        if (_isIdValid.value == true &&
            _isPasswordValid.value == true &&
            _isNicknameValid.value == true &&
            _isMbtiValid.value == true
        ) {
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
        } else {
            // 어떠한 입력 유효성도 통과하지 못하면 회원가입 결과를 false로 설정
            _signupResult.value = false
        }
    }

    private fun updateSignupResult() {
        // 입력 유효성이 변경될 때마다 결과값 갱신
        _signupResult.value = _isIdValid.value == true &&
                _isPasswordValid.value == true &&
                _isNicknameValid.value == true &&
                _isMbtiValid.value == true
    }

    fun updateSignupButtonState() {
        // Set the value based on the validity of ID, password, nickname, and MBTI
        _isSignUpButtonEnabled.value = _isIdValid.value == true &&
                _isPasswordValid.value == true &&
                _isNicknameValid.value == true &&
                _isMbtiValid.value == true
    }

    // 추가된 함수: 비밀번호 에러 메시지 갱신
    fun updatePasswordError(errorMessage: String) {
        _passwordErrorMessage.value = errorMessage
    }
}