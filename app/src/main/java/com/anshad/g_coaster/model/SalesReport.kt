package com.anshad.g_coaster.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SalesReport {
    @SerializedName("noofbills")
    @Expose
    var noofbills: String? = null
    @SerializedName("total")
    @Expose
    var total: String? = null
    @SerializedName("saledate")
    @Expose
    var saledate: String? = null
}