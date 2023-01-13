package com.anshad.g_coaster.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SoldItemsModel {
    @SerializedName("result")
    @Expose
    var result: ArrayList<SoldItems>? = null
}