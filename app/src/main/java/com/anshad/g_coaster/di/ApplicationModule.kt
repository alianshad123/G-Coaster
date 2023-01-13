package com.anshad.g_coaster.di

import android.content.Context
import androidx.room.Room
import com.anshad.g_coaster.BuildConfig
import com.anshad.g_coaster.api.ApiService
import com.anshad.g_coaster.api.AuthInterceptor
import com.anshad.g_coaster.api.ErrorInterceptor
import com.anshad.g_coaster.api.MockInterceptor
import com.anshad.g_coaster.data.repositories.PreferenceProvider
import com.anshad.g_coaster.db.CartDao
import com.anshad.g_coaster.db.CartDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {


    @Singleton
    @Provides
    fun retrofit(preference: PreferenceProvider): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE//Http Log

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(AuthInterceptor(preference,BuildConfig.BASE_URL))
            .addInterceptor(ErrorInterceptor(BuildConfig.BASE_URL))
            .addInterceptor(MockInterceptor(BuildConfig.BASE_URL))
            .hostnameVerifier(HostnameVerifier { _, _ -> true })
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }



    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CartDatabase {
        return Room.databaseBuilder(
            appContext,
            CartDatabase::class.java,
            CartDatabase.DB_NAME
        ).build()
    }

}