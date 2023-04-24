package com.anshad.g_coaster.data.datasource.sales

import com.anshad.basestructure.model.APIResult
import com.anshad.g_coaster.data.repositories.SalesRepository
import com.anshad.g_coaster.model.*
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SalesDataSource @Inject constructor(
    private val remote: SalesDataSource.Remote,
) : SalesRepository {
    interface Remote {
        fun getOutofStocks(pageLimit: PageLimit): Single<APIResult<ItemsModelData>>
        fun getSalesReport(): Single<APIResult<SalesReportModel>>
        fun getSales(request: SaleFilterDateModel): Single<APIResult<SoldItemsModel>>
        fun getBills(request: SaleFilterDateModel): Single<APIResult<BillsListModel>>
        fun getSalesByBills(request: SalesRequest): Single<APIResult<SoldItemsModel>>

    }

    override fun getOutofStocks(pageLimit: PageLimit): Single<APIResult<ItemsModelData>> {
        return remote.getOutofStocks(pageLimit)
    }

    override fun getSalesReport(): Single<APIResult<SalesReportModel>> {

        return remote.getSalesReport()
    }

    override fun getSales(request: SaleFilterDateModel): Single<APIResult<SoldItemsModel>> {

        return remote.getSales(request)
    }

    override fun getBills(request: SaleFilterDateModel): Single<APIResult<BillsListModel>> {
        return remote.getBills(request)
    }

    override fun getSalesByBills(request: SalesRequest): Single<APIResult<SoldItemsModel>> {
        return remote.getSalesByBills(request)
    }
}