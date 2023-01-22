package com.anshad.g_coaster.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchItem (
    @SerializedName("search")
    @Expose
    var search: String? = ""
)