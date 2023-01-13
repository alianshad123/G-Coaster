package com.anshad.g_coaster.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CartModel() : Parcelable {
    @SerializedName("cartitem")
    @Expose
    var cartitem: String? = null

    constructor(parcel: Parcel) : this() {
        cartitem = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cartitem)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartModel> {
        override fun createFromParcel(parcel: Parcel): CartModel {
            return CartModel(parcel)
        }

        override fun newArray(size: Int): Array<CartModel?> {
            return arrayOfNulls(size)
        }
    }
}
