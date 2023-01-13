package com.anshad.g_coaster.data.repositories

import androidx.lifecycle.LiveData
import com.anshad.basestructure.model.APIResult
import com.anshad.g_coaster.db.Cart
import com.anshad.g_coaster.model.AddItemModel
import com.anshad.g_coaster.model.SalesItemsModel
import com.anshad.g_coaster.model.SalesModel
import com.anshad.g_coaster.model.SalesResponseModel
import io.reactivex.rxjava3.core.Single

interface CartRepository {
    suspend fun insertCart(cart: Cart)
    suspend fun getAllCartItems(): LiveData<List<Cart>>
    suspend fun deleteCartItem(cart: Cart)
    suspend fun clearCart()

    fun updateSale(request: SalesModel): Single<APIResult<SalesResponseModel>>
    fun updateSaleItems(saleItems: List<SalesItemsModel>): Single<APIResult<String>>


}