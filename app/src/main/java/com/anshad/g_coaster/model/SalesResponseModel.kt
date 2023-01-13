package com.anshad.g_coaster.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SalesResponseModel {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null
    @SerializedName("billDate")
    @Expose
    var billdate: String? = null
    @SerializedName("billAmount")
    @Expose
    var billamount: Double? = null
    @SerializedName("grosstotal")
    @Expose
    var grosstotal: Double? = null
    @SerializedName("discount")
    @Expose
    var discount: Int? = null
    @SerializedName("roundoff")
    @Expose
    var roundoff: Double? = null
}