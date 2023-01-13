package com.anshad.g_coaster.data.datasource.items

import com.anshad.basestructure.model.APIResult
import com.anshad.g_coaster.data.repositories.ItemsRepository
import com.anshad.g_coaster.data.repositories.PreferenceProvider
import com.anshad.g_coaster.model.AddItemModel
import com.anshad.g_coaster.model.ItemsModel
import com.anshad.g_coaster.model.ItemsModelData
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ItemsDataSource@Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val remote: ItemsDataSource.Remote,
) : ItemsRepository {

    interface Remote {
        fun getItems(): Single<APIResult<ItemsModelData>>
        fun deleteItem(request: AddItemModel): Single<APIResult<String>>
        fun getItemById(itemId: AddItemModel): Single<APIResult<ItemsModel>>
    }

    override fun getItems(): Single<APIResult<ItemsModelData>> {
        return remote.getItems()

    }

    override fun deleteItem(request: AddItemModel): Single<APIResult<String>> {
        return remote.deleteItem(request)
    }

    override fun getItemById(request: AddItemModel): Single<APIResult<ItemsModel>> {
        return remote.getItemById(request)
    }


}