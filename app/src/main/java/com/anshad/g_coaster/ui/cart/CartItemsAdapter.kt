package com.anshad.g_coaster.ui.cart

import android.view.View
import com.anshad.g_coaster.R
import com.anshad.g_coaster.databinding.CartitemListBinding
import com.anshad.g_coaster.db.Cart
import com.anshad.g_coaster.utils.BaseAdapter

class CartItemsAdapter(private val list: List<Cart>,
                       val callBack: ItemClickListner
): BaseAdapter<CartitemListBinding, Cart>(list) {
    override val layoutId: Int = R.layout.cartitem_list

    override fun bind(binding: CartitemListBinding, item: Cart) {
        binding.apply {
            itemData = item
            binding.listner = callBack
            executePendingBindings()
        }
    }


}

interface ItemClickListner {
    fun onDataItemClicked(itemData: Cart)
}