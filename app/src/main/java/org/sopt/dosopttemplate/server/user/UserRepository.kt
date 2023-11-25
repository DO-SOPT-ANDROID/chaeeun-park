package org.sopt.dosopttemplate.server.user

class UserRepository(private val userApiManage: UserApiManage) {
    fun getUserList(page: Int, onResponse: (Boolean, UserResp) -> Unit) =
        userApiManage.getUserList(page, onResponse)
}