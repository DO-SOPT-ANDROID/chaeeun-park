package org.sopt.dosopttemplate.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpReq(
    @SerialName("username")
    val username: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("password")
    val password: String
)
