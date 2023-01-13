package com.anshad.g_coaster.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SalesModel (
    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null,
    @SerializedName("billamount")
    @Expose
    var billamount: Double? = null,
    @SerializedName("grosstotal")
    @Expose
    var grosstotal: Double? = null,
    @SerializedName("discount")
    @Expose
    var discount: Int? = null,
    @SerializedName("roundoff")
    @Expose
    var roundoff: Double? = null
)