package org.sopt.dosopttemplate.server.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.dosopttemplate.server.ResultResp

@Serializable
data class LoginResp(
    @SerialName("id")
    val id: Int,
    @SerialName("username")
    val username: String,
    @SerialName("nickname")
    val nickname: String
): ResultResp()