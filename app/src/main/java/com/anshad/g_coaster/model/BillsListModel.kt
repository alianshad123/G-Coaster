package com.anshad.g_coaster.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BillsListModel {
    @SerializedName("result")
    @Expose
    var result: ArrayList<BillList>? = null
}