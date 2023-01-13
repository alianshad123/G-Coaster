package com.anshad.g_coaster.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SalesItemsModel (
    @SerializedName("itemId")
    @Expose
    var itemId: Int? = null,
    @SerializedName("saleId")
    @Expose
    var saleId: Int? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("codename")
    @Expose
    val codename: String? = null,
    @SerializedName("sellingprize")
    @Expose
    val sellingprize: Double? = null,
    @SerializedName("quantity")
    @Expose
    val quantity: Int? = null,
    @SerializedName("size")
    @Expose
    val size: Int? = null,
    @SerializedName("color")
    @Expose
    val color: String? = null

)