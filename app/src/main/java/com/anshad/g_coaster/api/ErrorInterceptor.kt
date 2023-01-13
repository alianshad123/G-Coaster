package com.anshad.g_coaster.api

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONException
import org.json.JSONObject

class ErrorInterceptor ( private val baseUrl:String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (chain.request().url.toUri().toString().startsWith(baseUrl)) {
            response?.let {
                val str = response.body?.string()
                return try {

                    val json = JSONObject(str)
                    if (json.optInt("code", 200) != 200) {
                        if (json.has("data")) {
                            json.put("reason", json.optString("data", ""))
                            json.remove("data")
                        }
                    }
                    buildResponse(response.code, chain.request(), json)
                } catch (ex: JSONException) {
                    ex.printStackTrace()
                    buildResponse(response.code, chain.request(),str)
                }
            }
        }
        return response
    }

    private fun buildResponse(code: Int, request: Request, json: JSONObject): Response {
        return Response.Builder()
            .code(code)
            .message(json.optString("message"))
            .request(request)
            .protocol(Protocol.HTTP_1_0)
            .body(
                json.toString().toByteArray().toResponseBody("application/json".toMediaTypeOrNull())
            )
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun buildResponse(code: Int, request: Request, message: String?): Response {
        return Response.Builder()
            .code(code)
            .message(message?:"")
            .request(request)
            .protocol(Protocol.HTTP_1_0)
            .body(message?.toByteArray()?.let {
                it
                    .toResponseBody("application/json".toMediaTypeOrNull())
            })
            .addHeader("content-type", "application/json")
            .build()
    }
}