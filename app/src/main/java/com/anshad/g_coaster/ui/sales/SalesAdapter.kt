package com.anshad.g_coaster.ui.sales

import com.anshad.g_coaster.R
import com.anshad.g_coaster.databinding.SaleItemListBinding
import com.anshad.g_coaster.databinding.SalesListItemBinding
import com.anshad.g_coaster.model.ItemsModel
import com.anshad.g_coaster.model.SalesItemsModel
import com.anshad.g_coaster.model.SoldItems
import com.anshad.g_coaster.model.SoldItemsModel
import com.anshad.g_coaster.utils.BaseAdapter

class SalesAdapter (private val list: List<SoldItems>,
                    val callBack: ItemClickListner
): BaseAdapter<SalesListItemBinding, SoldItems>(list) {
    override val layoutId: Int = R.layout.sales_list_item

    override fun bind(binding: SalesListItemBinding, item: SoldItems) {
        binding.apply {
            salesData = item
            binding.listner = callBack
            executePendingBindings()
        }
    }


}

interface ItemClickListner {
    fun onDataItemClicked(salesData: SoldItems)
}