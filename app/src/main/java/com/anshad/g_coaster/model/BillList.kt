package com.anshad.g_coaster.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BillList {
    @SerializedName("id")
    @Expose
    var id: Long? = null
    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null
    @SerializedName("billdate")
    @Expose
    var billdate: String? = null
    @SerializedName("billamount")
    @Expose
    var billamount: Int? = null
    @SerializedName("grosstotal")
    @Expose
    var grosstotal: Int? = null
    @SerializedName("discount")
    @Expose
    var discount: Int? = null
    @SerializedName("roundoff")
    @Expose
    var roundoff: Int? = null
}