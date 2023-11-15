package org.sopt.dosopttemplate.server.user

import retrofit2.Call
import retrofit2.Response

class UserApiManage(private val userServiceAPI: UserServiceAPI) {
    fun getUserList(page: Int, onResponse:(Boolean, UserResp) -> Unit) {
        userServiceAPI.getUserList(page).enqueue(object: retrofit2.Callback<UserResp> {
            override fun onResponse(call: Call<UserResp>, response: Response<UserResp>) {
                if (response.isSuccessful && response.code() == 200) {
                    onResponse(response.isSuccessful, response.body() ?: UserResp())
                } else {
                    onResponse(response.isSuccessful, response.body() ?: UserResp())
                }
            }

            override fun onFailure(call: Call<UserResp>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}