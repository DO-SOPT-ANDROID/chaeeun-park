package org.sopt.dosopttemplate.server

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthServiceAPI {
    @POST("api/v1/members")
    fun signUp(
        @Body signUpReq: SignUpReq,
    ): Call<Unit>
    @POST("api/v1/members/sign-in")
    fun login(
        @Body loginReq: LoginReq
    ): Call<LoginResp>
}