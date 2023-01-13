package com.anshad.g_coaster.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.Date

class AddItemModel(
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("codename")
    @Expose
    val codename: String? = null,
    @SerializedName("costprize")
    @Expose
    val costprize: Double? = null,
    @SerializedName("sellingprize")
    @Expose
    val sellingprize: Double? = null,
    @SerializedName("quantity")
    @Expose
    val quantity: Int? = null,
    @SerializedName("size")
    @Expose
    val size: Int? = null,
    @SerializedName("isDeleted")
    @Expose
    val isDeleted: Int? = null,
    @SerializedName("color")
    @Expose
    val color: String? = null

)