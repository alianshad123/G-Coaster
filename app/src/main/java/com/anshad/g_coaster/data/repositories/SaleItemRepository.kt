package com.anshad.g_coaster.data.repositories

import androidx.lifecycle.LiveData
import com.anshad.basestructure.model.APIResult
import com.anshad.g_coaster.db.Cart
import com.anshad.g_coaster.db.Items
import com.anshad.g_coaster.model.AddItemModel
import com.anshad.g_coaster.model.ItemsModel
import io.reactivex.rxjava3.core.Single

interface SaleItemRepository {
    fun updateItem(addItemModel: AddItemModel): Single<APIResult<String>>

    suspend fun insertItems(result: List<Items>)
    suspend fun getAllItems(): LiveData<List<Items>>
}