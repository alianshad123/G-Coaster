package com.anshad.g_coaster.data.repositories

import com.anshad.basestructure.model.APIResult
import com.anshad.g_coaster.model.AddItemModel
import com.anshad.g_coaster.model.ItemsModel
import com.anshad.g_coaster.model.ItemsModelData
import com.anshad.g_coaster.model.SearchItem
import io.reactivex.rxjava3.core.Single

interface ItemsRepository {
    fun getItems() : Single<APIResult<ItemsModelData>>
    fun deleteItem(addItemModel: AddItemModel): Single<APIResult<String>>
    fun getItemById(itemId: AddItemModel): Single<APIResult<ItemsModel>>
    fun searchItem(request: SearchItem):  Single<APIResult<ItemsModelData>>
}