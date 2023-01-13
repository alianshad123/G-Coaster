package com.anshad.g_coaster.data.datasource.saleitem

import com.anshad.basestructure.datasources.BaseRemote
import com.anshad.basestructure.ktx.Observable.applyNetworkSchedulers
import com.anshad.basestructure.model.APIResult
import com.anshad.g_coaster.api.ApiService
import com.anshad.g_coaster.data.datasource.additem.AddItemDataSource
import com.anshad.g_coaster.model.AddItemModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SaleItemRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    BaseRemote(), SaleItemDataSource.Remote {
    override fun updateitem(request: AddItemModel): Single<APIResult<String>> {
        return apiService.updateItems(request).createResult().applyNetworkSchedulers()
    }

}