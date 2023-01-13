package com.anshad.g_coaster.data.repositories

import com.anshad.basestructure.model.APIResult
import com.anshad.g_coaster.model.AddItemModel
import com.anshad.g_coaster.model.AddItemResponseModel
import io.reactivex.rxjava3.core.Single

    interface AddItemRepository {
    fun additems(addItemModel: AddItemModel): Single<APIResult<String>>
        fun updateitems(addItemModel: AddItemModel): Single<APIResult<String>>
        fun additemsArray(itemsData: ArrayList<AddItemModel>):  Single<APIResult<String>>
    }