package com.anshad.g_coaster.di.items

import com.anshad.g_coaster.data.datasource.additem.AddItemDataSource
import com.anshad.g_coaster.data.datasource.additem.AddItemRemoteDataSource
import com.anshad.g_coaster.data.datasource.items.ItemRemoteDataSource
import com.anshad.g_coaster.data.datasource.items.ItemsDataSource
import com.anshad.g_coaster.data.repositories.AddItemRepository
import com.anshad.g_coaster.data.repositories.ItemsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ItemsAbstractARM {
    @Binds
    @ViewModelScoped
    abstract fun itemsDataSource(impl: ItemsDataSource): ItemsRepository


    @Binds
    @ViewModelScoped
    abstract fun itemsRemoteDataSource(impl: ItemRemoteDataSource): ItemsDataSource.Remote
}