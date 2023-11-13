package org.sopt.dosopttemplate.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class RespResult(
    @SerialName("message")
    val message: String = ""
)