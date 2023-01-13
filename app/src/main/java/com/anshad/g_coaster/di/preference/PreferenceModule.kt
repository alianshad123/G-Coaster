package com.anshad.g_coaster.di.preference
import com.anshad.g_coaster.data.datasource.DefaultPreference
import com.anshad.g_coaster.data.repositories.PreferenceProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule {

    @Singleton
    @Binds
    abstract fun provideDefaultPreference(impl: DefaultPreference): PreferenceProvider

}