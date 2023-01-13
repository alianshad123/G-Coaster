package com.anshad.g_coaster.data.datasource.saleitem

import com.anshad.basestructure.model.APIResult
import com.anshad.g_coaster.data.datasource.additem.AddItemDataSource
import com.anshad.g_coaster.data.repositories.PreferenceProvider
import com.anshad.g_coaster.data.repositories.SaleItemRepository
import com.anshad.g_coaster.model.AddItemModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SaleItemDataSource@Inject constructor(private val preferenceProvider: PreferenceProvider,
                                            private val remote: SaleItemDataSource.Remote,):SaleItemRepository {
    interface Remote {
        fun updateitem(request: AddItemModel): Single<APIResult<String>>
    }

    override fun updateItem(addItemModel: AddItemModel): Single<APIResult<String>> {
        return remote.updateitem(addItemModel)
    }

}