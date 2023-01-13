package com.anshad.g_coaster.di.cart

import com.anshad.g_coaster.data.datasource.additem.AddItemDataSource
import com.anshad.g_coaster.data.datasource.additem.AddItemRemoteDataSource
import com.anshad.g_coaster.data.datasource.cart.CartDataSource
import com.anshad.g_coaster.data.datasource.cart.CartRemoteDataSource
import com.anshad.g_coaster.data.datasource.items.ItemsDataSource
import com.anshad.g_coaster.data.repositories.CartRepository
import com.anshad.g_coaster.data.repositories.ItemsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class CartAbstractARM {

    @Binds
    @ViewModelScoped
    abstract fun cartDataSource(impl: CartDataSource): CartRepository

    @Binds
    @ViewModelScoped
    abstract fun cartRemoteDataSource(impl: CartRemoteDataSource): CartDataSource.Remote

}