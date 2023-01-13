package com.anshad.g_coaster.data.datasource.cart

import com.anshad.basestructure.datasources.BaseRemote
import com.anshad.basestructure.ktx.Observable.applyNetworkSchedulers
import com.anshad.basestructure.model.APIResult
import com.anshad.g_coaster.api.ApiService
import com.anshad.g_coaster.data.datasource.additem.AddItemDataSource
import com.anshad.g_coaster.model.SalesItemsModel
import com.anshad.g_coaster.model.SalesModel
import com.anshad.g_coaster.model.SalesResponseModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CartRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    BaseRemote(), CartDataSource.Remote {
    override fun updateSale(request: SalesModel): Single<APIResult<SalesResponseModel>> {
        return apiService.updateSales(request).createResult().applyNetworkSchedulers()
    }

    override fun updateSaleItems(request: List<SalesItemsModel>): Single<APIResult<String>> {
        return apiService.updateSaleItems(request).createResult().applyNetworkSchedulers()
    }
}