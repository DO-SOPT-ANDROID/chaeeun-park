package org.sopt.dosopttemplate.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.sopt.dosopttemplate.server.user.UserRepository

class PeopleViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PeopleViewModel::class.java)) {
            return PeopleViewModel(userRepository) as T
        }
        throw IllegalArgumentException("알 수 없는 ViewModel 클래스: ${modelClass.name}")
    }
}