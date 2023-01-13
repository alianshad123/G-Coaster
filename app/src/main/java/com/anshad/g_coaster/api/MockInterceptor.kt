package com.anshad.g_coaster.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

class MockInterceptor(private val baseUrl:String) : Interceptor {
    object Temp {
        var data = 1;
    }
    private val  RESPONSE =  ""



    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var responseString: String? = null
        val response: Response?
        val uri = chain.request().url.toUri()
        when{
//            uri.toString().startsWith(baseUrl + APIUrls.DRIVER_PHONE_LOGIN) ->{
//                responseString = RESPONSE
//            }
        }

        responseString?.let {
            response = Response.Builder()
                .code(200)
                .message(it)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(it.toByteArray().toResponseBody("application/json".toMediaTypeOrNull()))
                .addHeader("content-type", "application/json")
                .build()

            Log.d("HTTP", "---- DEBUG --- DEBUG -- DEBUG - DEBUG -- DEBUG --- DEBUG ----")
            return response
        }
        return chain.proceed(chain.request())
    }

}