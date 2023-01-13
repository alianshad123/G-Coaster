package com.anshad.g_coaster.data.datasource.additem

import com.anshad.basestructure.model.APIResult
import com.anshad.g_coaster.data.repositories.AddItemRepository
import com.anshad.g_coaster.data.repositories.PreferenceProvider
import com.anshad.g_coaster.model.AddItemModel
import com.anshad.g_coaster.model.AddItemResponseModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AddItemDataSource@Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val remote: AddItemDataSource.Remote,
) : AddItemRepository {

    interface Remote {
        fun addItems(request:AddItemModel): Single<APIResult<String>>
        fun updateitems(request: AddItemModel): Single<APIResult<String>>
        fun additemsArray(itemsData: ArrayList<AddItemModel>): Single<APIResult<String>>
    }

    override fun additems(request: AddItemModel): Single<APIResult<String>> {
        return remote.addItems(request)
    }

    override fun updateitems(request: AddItemModel): Single<APIResult<String>> {
        return remote.updateitems(request)
    }

    override fun additemsArray(itemsData: ArrayList<AddItemModel>): Single<APIResult<String>> {
        return remote.additemsArray(itemsData)
    }


}