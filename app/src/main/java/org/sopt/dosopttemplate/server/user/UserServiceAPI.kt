package org.sopt.dosopttemplate.server.user

import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query

interface UserServiceAPI {
    @GET("api/users")
    fun getUserList(
        @Query("page") page: Int
    ): Call<UserResp>

}