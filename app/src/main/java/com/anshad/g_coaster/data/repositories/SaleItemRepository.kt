package com.anshad.g_coaster.data.repositories

import com.anshad.basestructure.model.APIResult
import com.anshad.g_coaster.model.AddItemModel
import io.reactivex.rxjava3.core.Single

interface SaleItemRepository {
    fun updateItem(addItemModel: AddItemModel): Single<APIResult<String>>
}