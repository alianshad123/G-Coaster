package com.anshad.g_coaster.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SaleFilterDateModel(
    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null,
)