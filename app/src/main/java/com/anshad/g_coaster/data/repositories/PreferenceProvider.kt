package com.anshad.g_coaster.data.repositories

interface PreferenceProvider {

    fun getAuthToken(): String?
    fun setAuthToken(token:String?)
}