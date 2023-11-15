package org.sopt.dosopttemplate.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.server.user.UserRepository
import org.sopt.dosopttemplate.server.user.UserDataResp

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userList = MutableLiveData<List<UserDataResp>>()
    val userList: LiveData<List<UserDataResp>> get() = _userList

    private val _errorState = MutableLiveData<Boolean>()
    val errorState: LiveData<Boolean> get() = _errorState

    fun getUserList(page: Int) {
        viewModelScope.launch {
            userRepository.getUserList(page) { success, userResp ->
                if (success) {
                    handleSuccess(userResp.data)
                } else {
                    handleFailure()
                }
            }
        }
    }

    private fun handleSuccess(userList: List<UserDataResp>) {
        _userList.postValue(userList)
        _errorState.postValue(false)
    }

    private fun handleFailure() {
        _userList.postValue(emptyList())
        _errorState.postValue(true)
    }
}
