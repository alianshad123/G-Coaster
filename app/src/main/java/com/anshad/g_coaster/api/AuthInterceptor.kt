package com.anshad.g_coaster.api

import android.util.Log
import com.anshad.g_coaster.data.repositories.PreferenceProvider
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val preference: PreferenceProvider, private val baseUrl: String) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (chain.request().url.toUri().toString().startsWith(baseUrl)) {
            if (preference.getAuthToken() != null) {
                builder.addHeader("token", preference.getAuthToken()!!)
                Log.v("OkHttp auth", preference.getAuthToken() ?: "")
            }

        }
        return chain.proceed(builder.build())
    }

}