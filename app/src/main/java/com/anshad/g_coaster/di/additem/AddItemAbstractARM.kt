package com.anshad.g_coaster.di.additem

import com.anshad.g_coaster.data.datasource.additem.AddItemDataSource
import com.anshad.g_coaster.data.datasource.additem.AddItemRemoteDataSource
import com.anshad.g_coaster.data.repositories.AddItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AddItemAbstractARM {

    @Binds
    @ViewModelScoped
    abstract fun addItemDataSource(impl: AddItemDataSource): AddItemRepository


    @Binds
    @ViewModelScoped
    abstract fun addItemRemoteDataSource(impl: AddItemRemoteDataSource): AddItemDataSource.Remote
}