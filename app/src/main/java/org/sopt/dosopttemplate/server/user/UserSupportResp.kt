package org.sopt.dosopttemplate.server.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSupportResp(
    @SerialName("url")
    val url: String = "",
    @SerialName("text")
    val text: String = "",
)