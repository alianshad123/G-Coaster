package com.anshad.g_coaster.data.datasource.items

import com.anshad.basestructure.model.APIResult
import com.anshad.g_coaster.data.repositories.ItemsRepository
import com.anshad.g_coaster.data.repositories.PreferenceProvider
import com.anshad.g_coaster.db.CartDatabase
import com.anshad.g_coaster.model.*
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ItemsDataSource@Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val remote: ItemsDataSource.Remote,
    private val cartDatabase: CartDatabase,
) : ItemsRepository {

    interface Remote {
        fun getItems(pageLimit: Int): Single<APIResult<ItemsModelData>>
        fun deleteItem(request: AddItemModel): Single<APIResult<String>>
        fun getItemById(itemId: AddItemModel): Single<APIResult<ItemsModel>>
        fun searchItem(request: SearchItem): Single<APIResult<ItemsModelData>>
    }

    override fun getItems(pageLimit: Int): Single<APIResult<ItemsModelData>> {
        return remote.getItems(pageLimit)

    }

   /* override fun getItems(): Single<APIResult<ItemsModelData>> {
        return remote.getItems()

    }
*/
    override fun deleteItem(request: AddItemModel): Single<APIResult<String>> {
        return remote.deleteItem(request)
    }

    override fun getItemById(request: AddItemModel): Single<APIResult<ItemsModel>> {
        return remote.getItemById(request)
    }

    override fun searchItem(request: SearchItem): Single<APIResult<ItemsModelData>> {
        return remote.searchItem(request)
    }


}