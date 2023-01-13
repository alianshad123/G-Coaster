package com.anshad.g_coaster.ui.salereport

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.anshad.basestructure.ui.BaseViewModel
import com.anshad.g_coaster.R
import com.anshad.g_coaster.data.repositories.SalesRepository
import com.anshad.g_coaster.model.SalesReport
import com.anshad.g_coaster.model.SalesReportModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SalesReportViewModel @Inject constructor(
    private val repository: SalesRepository
) : BaseViewModel() {

    val _salesReportData = MutableLiveData<SalesReportModel?>()
    val salesReportData: MutableLiveData<SalesReportModel?> = _salesReportData



    fun getSalesReport() {
        showLoading()
        repository.getSalesReport().subscribe({ apiResult ->
            hideLoading()
            if (apiResult.isSuccess) {

                _salesReportData.postValue(apiResult.data)
            } else {
                _salesReportData.postValue(null)


            }
        }, {
            hideLoading()

        })
    }

    fun showDetailedReport(salesReport: SalesReport) {
        val bundle=Bundle()
        bundle.putString("dateKey",salesReport.saledate)
        navigate(R.id.action_salesreportFragment_to_soldItemsFragment,bundle)

    }



}