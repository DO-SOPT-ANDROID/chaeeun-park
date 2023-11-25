package org.sopt.dosopttemplate.server.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDataResp(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("email")
    val email: String = "",
    @SerialName("first_name")
    val first_name: String = "",
    @SerialName("last_name")
    val last_name: String = "",
    @SerialName("avatar")
    val avatar: String = ""
)