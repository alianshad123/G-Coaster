package com.anshad.g_coaster.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SalesRequest (
    @SerializedName("saleId")
    @Expose
    var saleId: Long? = null
)