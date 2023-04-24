package com.anshad.g_coaster.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PageLimit (
    @SerializedName("pageLimit")
    @Expose
    var pageLimit: Int? = 0
        )