package com.anshad.g_coaster.ui.bills

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anshad.basestructure.model.Event
import com.anshad.basestructure.model.LoadingMessageData
import com.anshad.basestructure.ui.BaseViewModel
import com.anshad.g_coaster.R
import com.anshad.g_coaster.data.repositories.SalesRepository
import com.anshad.g_coaster.model.BillsListModel
import com.anshad.g_coaster.model.SaleFilterDateModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class BillsViewModel @Inject constructor(
    private val repository: SalesRepository
) : BaseViewModel() {

    var dateKey: String? =null
    var date:String?=null
    init {
        val format = SimpleDateFormat("yyyy-MM-dd")
         date=format.format(Date())

    }



    val _billsData = MutableLiveData<BillsListModel?>()
    val billsData: MutableLiveData<BillsListModel?> = _billsData

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


    fun getBills(filterDate: String) {
        showLoading_()
        repository.getBills(SaleFilterDateModel(updatedAt = filterDate)).subscribe({ apiResult ->
            hideLoading_()
            if (apiResult.isSuccess) {
               if(apiResult.data!=null){
                    _billsData.postValue(apiResult.data)
                }else{
                   _billsData.postValue(null)
               }


            } else {


            }
        }, {
            hideLoading_()

        })
    }

    fun navigateToSoldItems(date: String?, saleId: Long?) {
        val bundle= Bundle()
        bundle.putString("dateKey",date)
        bundle.putLong("saleId",saleId?:-1)
        navigate(R.id.action_billsFragment_to_soldItemsFragment,bundle)
    }
}