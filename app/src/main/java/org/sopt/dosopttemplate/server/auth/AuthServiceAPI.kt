package org.sopt.dosopttemplate.server.auth

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthServiceAPI {
    @POST("api/v1/members")
    fun signUp(
        @Body signUpReq: SignUpReq,
    ): Call<Unit>
    @POST("api/v1/members/sign-in")
   suspend fun login(
        @Body loginReq: LoginReq
    ): Response<LoginResp>
}