package com.anshad.g_coaster.data.datasource.additem

import com.anshad.basestructure.datasources.BaseRemote
import com.anshad.basestructure.ktx.Observable.applyNetworkSchedulers
import com.anshad.basestructure.model.APIResult
import com.anshad.g_coaster.api.ApiService
import com.anshad.g_coaster.model.AddItemModel
import com.anshad.g_coaster.model.AddItemResponseModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AddItemRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    BaseRemote(), AddItemDataSource.Remote {

    override fun addItems(request: AddItemModel): Single<APIResult<String>> {
        return apiService.addItems(request).createResult().applyNetworkSchedulers()
    }

    override fun updateitems(request: AddItemModel): Single<APIResult<String>> {
        return apiService.updateItems(request).createResult().applyNetworkSchedulers()
    }

    override fun additemsArray(itemsData: ArrayList<AddItemModel>): Single<APIResult<String>> {
        return apiService.additemsArray(itemsData).createResult().applyNetworkSchedulers()
    }


}