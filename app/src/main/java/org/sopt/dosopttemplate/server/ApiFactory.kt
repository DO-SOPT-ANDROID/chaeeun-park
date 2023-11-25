package org.sopt.dosopttemplate.server

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.sopt.dosopttemplate.BuildConfig
import org.sopt.dosopttemplate.server.ApiFactory.AUTH_BASE_URL
import org.sopt.dosopttemplate.server.ApiFactory.USER_BASE_URL
import org.sopt.dosopttemplate.server.auth.AuthServiceAPI
import org.sopt.dosopttemplate.server.user.UserServiceAPI
import retrofit2.Retrofit

object ApiFactory {
    const val AUTH_BASE_URL = BuildConfig.AUTH_BASE_URL
    const val USER_BASE_URL = BuildConfig.USER_BASE_URL

    private val httpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    fun getRetrofit(url: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()

    inline fun <reified T, B> create(url: B): T =
        getRetrofit(url.toString()).create<T>(T::class.java)
}

object ServicePool {
    val authService = ApiFactory.create<AuthServiceAPI, String>(AUTH_BASE_URL)
    val userService = ApiFactory.create<UserServiceAPI, String>(USER_BASE_URL)
}




