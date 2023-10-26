package org.sopt.dosopttemplate.di

import android.content.Context
import android.content.SharedPreferences

object UserSharedPreferences {
    private val MY_ACCOUNT: String = "account"

    fun clearUser(context: Context) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}
