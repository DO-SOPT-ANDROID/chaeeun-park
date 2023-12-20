package org.sopt.dosopttemplate.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.server.ServicePool
import org.sopt.dosopttemplate.server.auth.LoginReq
import org.sopt.dosopttemplate.server.auth.LoginResp

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableStateFlow<Result<LoginResp>>(Result.Idle)
    val loginResult = _loginResult.asStateFlow()

    fun performLogin(
        userId: String,
        userPw: String
    ) {
        viewModelScope.launch {
            try {
                val loginReq = LoginReq(userId, userPw)
                _loginResult.value = Result.Success(
                    ServicePool.authService.login(loginReq).body()
                        ?: throw Exception("값을 입력해주세요.")
                )
            } catch (e: Exception) {
                _loginResult.value = Result.Error(e)
            }
        }
    }
}