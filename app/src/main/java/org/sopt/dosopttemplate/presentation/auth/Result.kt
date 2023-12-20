package org.sopt.dosopttemplate.presentation.auth

sealed class Result<out T> {
    object Idle : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}