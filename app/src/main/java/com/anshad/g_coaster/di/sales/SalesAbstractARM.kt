package com.anshad.g_coaster.di.sales

import com.anshad.g_coaster.data.datasource.saleitem.SaleItemDataSource
import com.anshad.g_coaster.data.datasource.saleitem.SaleItemRemoteDataSource
import com.anshad.g_coaster.data.datasource.sales.SalesDataSource
import com.anshad.g_coaster.data.datasource.sales.SalesRemoteDataSource
import com.anshad.g_coaster.data.repositories.SaleItemRepository
import com.anshad.g_coaster.data.repositories.SalesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class SalesAbstractARM {
    @Binds
    @ViewModelScoped
    abstract fun saleItemDataSource(impl: SalesDataSource): SalesRepository


    @Binds
    @ViewModelScoped
    abstract fun saleItemRemoteDataSource(impl: SalesRemoteDataSource): SalesDataSource.Remote
}