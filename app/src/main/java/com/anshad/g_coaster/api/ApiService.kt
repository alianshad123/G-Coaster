package com.anshad.g_coaster.api

import com.anshad.basestructure.model.DefaultResponse
import com.anshad.g_coaster.constants.enums.ApiUrls
import com.anshad.g_coaster.model.*
import io.reactivex.rxjava3.core.Single
import retrofit2.adapter.rxjava3.Result
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST(ApiUrls.ADD_ITEM)
    fun addItems(@Body request: AddItemModel): Single<Result<DefaultResponse<String>>>

   /* @GET(ApiUrls.ITEMS)
    fun getItems(): Single<Result<DefaultResponse<ItemsModelData>>>
*/    @GET(ApiUrls.ITEMS)
    fun getItems(@Query("pageLimit") pageLimit: Int?): Single<Result<DefaultResponse<ItemsModelData>>>
    @POST(ApiUrls.UPDATE_ITEM)
    fun updateItems(@Body request: AddItemModel): Single<Result<DefaultResponse<String>>>

    @POST(ApiUrls.DELETE_ITEM)
    fun deleteItem(@Body request: AddItemModel): Single<Result<DefaultResponse<String>>>

    @POST(ApiUrls.ADD_ITEM_ARRAY)
    fun additemsArray(@Body itemsData: ArrayList<AddItemModel>): Single<Result<DefaultResponse<String>>>

    @POST(ApiUrls.GET_ITEM_BY_ID)
    fun getItemById(@Body request: AddItemModel): Single<Result<DefaultResponse<ItemsModel>>>

    @POST(ApiUrls.ADD_SALE)
    fun updateSales(@Body request: SalesModel):  Single<Result<DefaultResponse<SalesResponseModel>>>

    @POST(ApiUrls.ADD_SALEITEMS)
    fun updateSaleItems(@Body request: List<SalesItemsModel>): Single<Result<DefaultResponse<String>>>

    @POST(ApiUrls.OUTOFFSTOCKS)
    fun getOutofStocks(@Body request: PageLimit): Single<Result<DefaultResponse<ItemsModelData>>>

    @GET(ApiUrls.SALESREPORT)
    fun getSalesReport(): Single<Result<DefaultResponse<SalesReportModel>>>

    @POST(ApiUrls.SOLDITEMS)
    fun getSales(@Body request: SaleFilterDateModel): Single<Result<DefaultResponse<SoldItemsModel>>>

    @POST(ApiUrls.GET_BILLS)
    fun getBills(@Body request: SaleFilterDateModel): Single<Result<DefaultResponse<BillsListModel>>>

    @POST(ApiUrls.GET_SALESBY_BILLS)
    fun getSalesByBills(@Body request: SalesRequest): Single<Result<DefaultResponse<SoldItemsModel>>>

    @POST(ApiUrls.SEARCH_ITEM)
    fun searchItem(@Body request: SearchItem): Single<Result<DefaultResponse<ItemsModelData>>>

    @POST(ApiUrls.UPDATE_SALES)
    fun updateSalePrint(@Body request: SalesUpdate): Single<Result<DefaultResponse<String>>>

}