package com.anshad.g_coaster.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cart_table")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @SerializedName("id")
    @Expose
    var itemId: Int? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("codename")
    @Expose
    var codename: String? = null,
    @SerializedName("costprize")
    @Expose
    var costprize: Double? = null,
    @SerializedName("sellingprize")
    @Expose
    var sellingprize: Double? = null,
    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null,
    @SerializedName("size")
    @Expose
    var size: Int? = null,
    @SerializedName("color")
    @Expose
    var color: String? = null
)


