package com.anshad.g_coaster.data.datasource.saleitem

import androidx.lifecycle.LiveData
import com.anshad.basestructure.model.APIResult
import com.anshad.g_coaster.data.datasource.additem.AddItemDataSource
import com.anshad.g_coaster.data.repositories.PreferenceProvider
import com.anshad.g_coaster.data.repositories.SaleItemRepository
import com.anshad.g_coaster.db.CartDatabase
import com.anshad.g_coaster.db.Items
import com.anshad.g_coaster.model.AddItemModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SaleItemDataSource@Inject constructor(private val preferenceProvider: PreferenceProvider,
                                            private val database: CartDatabase,
                                            private val remote: SaleItemDataSource.Remote,):SaleItemRepository {
    interface Remote {
        fun updateitem(request: AddItemModel): Single<APIResult<String>>
    }

    override fun updateItem(addItemModel: AddItemModel): Single<APIResult<String>> {
        return remote.updateitem(addItemModel)
    }

    override suspend fun insertItems(result: List<Items>) {
        database.getItemsDao().insertAll(result)
    }

    override suspend fun getAllItems(): LiveData<List<Items>> {
       return database.getItemsDao().getAllItems()
    }

}