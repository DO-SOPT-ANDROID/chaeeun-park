package org.sopt.dosopttemplate.di

import android.content.Context
import android.content.SharedPreferences

object UserSharedPreferences {
    private const val MY_ACCOUNT: String = "account"
    const val USER_ID = "USER_ID"
    const val USER_PW = "USER_PW"
    const val USER_NICKNAME = "USER_NICKNAME"
    const val USER_MBTI = "USER_MBTI"
    private const val LOGGED_IN = "LOGGED_IN"

    fun setUserID(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(USER_ID, input)
        editor.apply()
    }

    fun getUserID(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString(USER_ID, "").toString()
    }

    fun setUserPw(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(USER_PW, input)
        editor.apply()
    }

    fun getUserPw(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString(USER_PW, "").toString()
    }

    fun setUserNickname(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(USER_NICKNAME, input)
        editor.apply()
    }

    fun getUserNickname(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString(USER_NICKNAME, "").toString()
    }

    fun setUserMbti(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(USER_MBTI, input)
        editor.apply()
    }

    fun getUserMbti(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString(USER_MBTI, "").toString()
    }

    fun isLoggedIn(context: Context): Boolean {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getBoolean(LOGGED_IN, false)
    }

    fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putBoolean(LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun clearUser(context: Context) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}
