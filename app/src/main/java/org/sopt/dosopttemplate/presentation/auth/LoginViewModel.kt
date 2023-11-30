package org.sopt.dosopttemplate.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.server.ServicePool
import org.sopt.dosopttemplate.server.auth.LoginReq
import org.sopt.dosopttemplate.server.auth.LoginResp


class LoginViewModel : ViewModel() {

    /*
    fun login(id: String, password: String) {
    viewModelScope.launch {
        try {
            val response = authService.login(RequestLoginDto(id, password))
            if (response.isSuccessful) {
                loginResult.value = response.body()
                loginSuccess.value = true
            } else {
                loginSuccess.value = false
            }
        } catch (e: Exception) {
            // TODO: 에러 처리 로직
        }
    }
}*/
    fun performLogin(
        userId: String,
        userPw: String,
        onSuccess: (LoginResp) -> Unit,
        onFailure: () -> Unit,
        onNetworkError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val loginReq = LoginReq(userId, userPw)
                val response = ServicePool.authService.login(loginReq)
                if (response.isSuccessful) {
                    val loginResp = response.body()
                    if (loginResp != null) {
                        onSuccess(loginResp)
                    } else {
                        onFailure()
                    }
                } else {
                    onFailure()
                }

            } catch (e: Exception) {
                onNetworkError()
            }
        }
    }
}