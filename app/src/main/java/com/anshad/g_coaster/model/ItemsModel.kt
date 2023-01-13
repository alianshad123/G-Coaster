package com.anshad.g_coaster.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ItemsModel : Serializable {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("codename")
    @Expose
    var codename: String? = null
    @SerializedName("costprize")
    @Expose
    var costprize: Double? = null
    @SerializedName("sellingprize")
    @Expose
    var sellingprize: Double? = null
    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null
    @SerializedName("size")
    @Expose
    var size: Int? = null
    @SerializedName("isDeleted")
    @Expose
    var isDeleted: Int? = null
    @SerializedName("color")
    @Expose
    val color: String? = null

    fun getQuantityData(): String? {
        if((quantity ?: 0) > 0){
            return "Quantity: $quantity"
        }else{
            return "Out of Stock"
        }
    }

}