package com.anshad.g_coaster.ui.sales

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anshad.basestructure.model.Event
import com.anshad.basestructure.model.LoadingMessageData
import com.anshad.basestructure.ui.BaseViewModel
import com.anshad.g_coaster.R
import com.anshad.g_coaster.data.repositories.*
import com.anshad.g_coaster.model.SaleFilterDateModel
import com.anshad.g_coaster.model.SalesReportModel
import com.anshad.g_coaster.model.SalesRequest
import com.anshad.g_coaster.model.SoldItemsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val repository: SalesRepository
) : BaseViewModel() {
    var saleId: Long?=null
    var dateKey: String? =null
    val _salesData = MutableLiveData<SoldItemsModel?>()
    val salesData: MutableLiveData<SoldItemsModel?> = _salesData
    var date:String?=null
    private val loadingLiveData = MutableLiveData<Event<LoadingMessageData>>()
    val loading_: LiveData<Event<LoadingMessageData>> = loadingLiveData

    fun showLoading_(message: LoadingMessageData) {
        loadingLiveData.value = Event(message)
    }

    fun showLoading_(@StringRes message: Int = R.string.loading) {
        val messageData = LoadingMessageData()
        messageData.isLoading = true
        messageData.messageRes = message
        showLoading_(messageData)
    }

    fun hideLoading_() {
        val messageData = LoadingMessageData()
        messageData.isLoading = false
        showLoading_(messageData)
    }

    fun getSales(saleId: Long?) {
        showLoading_()
        repository.getSalesByBills(SalesRequest(saleId = saleId)).subscribe({ apiResult ->
            hideLoading_()
            if (apiResult.isSuccess) {

                _salesData.postValue(apiResult.data)
            } else {
                _salesData.postValue(null)


            }
        }, {
            hideLoading_()

        })
    }

}