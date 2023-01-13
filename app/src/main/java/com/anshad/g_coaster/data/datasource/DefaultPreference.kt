package com.anshad.g_coaster.data.datasource

import android.content.Context
import com.anshad.basestructure.ktx.PreferenceHelper
import com.anshad.basestructure.ktx.PreferenceHelper.get
import com.anshad.basestructure.ktx.PreferenceHelper.set
import com.anshad.g_coaster.data.repositories.PreferenceProvider

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultPreference @Inject constructor(@ApplicationContext context: Context) :
    PreferenceProvider {

    object Fields {
        const val AUTH_TOKEN = "auth_token"
    }

    private val sharedPreferences by lazy {
        PreferenceHelper.customPrefs(context, "")
    }

    override fun getAuthToken(): String? {
        return sharedPreferences[Fields.AUTH_TOKEN]
    }

    override fun setAuthToken(token: String?) {
        sharedPreferences[Fields.AUTH_TOKEN] = token
    }


}