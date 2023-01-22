package com.anshad.g_coaster.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SalesUpdate (
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("print")
    @Expose
    var print: String? = null,
)