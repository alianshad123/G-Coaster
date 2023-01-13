package com.anshad.g_coaster.data.datasource.items

import com.anshad.basestructure.datasources.BaseRemote
import com.anshad.basestructure.ktx.Observable.applyNetworkSchedulers
import com.anshad.basestructure.model.APIResult
import com.anshad.g_coaster.api.ApiService
import com.anshad.g_coaster.model.AddItemModel
import com.anshad.g_coaster.model.ItemsModel
import com.anshad.g_coaster.model.ItemsModelData
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ItemRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    BaseRemote(), ItemsDataSource.Remote {
    override fun getItems(): Single<APIResult<ItemsModelData>> {
        return apiService.getItems().createResult().applyNetworkSchedulers()
    }

    override fun deleteItem(request: AddItemModel): Single<APIResult<String>> {
        return apiService.deleteItem(request).createResult().applyNetworkSchedulers()
    }

    override fun getItemById(request: AddItemModel): Single<APIResult<ItemsModel>> {
        return apiService.getItemById(request).createResult().applyNetworkSchedulers()
    }


}