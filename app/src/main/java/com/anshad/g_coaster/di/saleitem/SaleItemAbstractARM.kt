package com.anshad.g_coaster.di.saleitem

import com.anshad.g_coaster.data.datasource.additem.AddItemDataSource
import com.anshad.g_coaster.data.datasource.additem.AddItemRemoteDataSource
import com.anshad.g_coaster.data.datasource.saleitem.SaleItemDataSource
import com.anshad.g_coaster.data.datasource.saleitem.SaleItemRemoteDataSource
import com.anshad.g_coaster.data.repositories.AddItemRepository
import com.anshad.g_coaster.data.repositories.SaleItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class SaleItemAbstractARM {
    @Binds
    @ViewModelScoped
    abstract fun saleItemDataSource(impl: SaleItemDataSource): SaleItemRepository


    @Binds
    @ViewModelScoped
    abstract fun saleItemRemoteDataSource(impl: SaleItemRemoteDataSource): SaleItemDataSource.Remote
}