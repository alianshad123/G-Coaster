package com.anshad.g_coaster.data.datasource.cart

import androidx.lifecycle.LiveData
import com.anshad.basestructure.model.APIResult
import com.anshad.g_coaster.data.datasource.additem.AddItemDataSource
import com.anshad.g_coaster.data.repositories.AddItemRepository
import com.anshad.g_coaster.data.repositories.CartRepository
import com.anshad.g_coaster.data.repositories.PreferenceProvider
import com.anshad.g_coaster.db.Cart
import com.anshad.g_coaster.db.CartDatabase
import com.anshad.g_coaster.model.SalesItemsModel
import com.anshad.g_coaster.model.SalesModel
import com.anshad.g_coaster.model.SalesResponseModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CartDataSource @Inject constructor(
 private val cartDatabase: CartDatabase,
 private val remote: CartDataSource.Remote,
) : CartRepository {

    interface Remote {
        fun updateSale(request: SalesModel): Single<APIResult<SalesResponseModel>>
        fun updateSaleItems(request: List<SalesItemsModel>): Single<APIResult<String>>
    }
    override suspend fun insertCart(cart: Cart) {
         cartDatabase.getCartDao().insertCart(cart)
    }

    override suspend fun getAllCartItems(): LiveData<List<Cart>> {
        return  cartDatabase.getCartDao().getAllCarts()
    }

    override suspend fun deleteCartItem(cart: Cart) {
          cartDatabase.getCartDao().deleteCartById(cart.id?:-1)
    }

    override suspend fun clearCart() {
        cartDatabase.getCartDao().clearCart()
    }

    override fun updateSale(request: SalesModel): Single<APIResult<SalesResponseModel>> {
        return remote.updateSale(request)
    }

    override fun updateSaleItems(request: List<SalesItemsModel>): Single<APIResult<String>> {
        return remote.updateSaleItems(request)
    }

}