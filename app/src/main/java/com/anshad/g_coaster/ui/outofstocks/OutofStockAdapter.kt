package com.anshad.g_coaster.ui.outofstocks

import com.anshad.g_coaster.R
import com.anshad.g_coaster.databinding.OutofstockListitemBinding
import com.anshad.g_coaster.databinding.SaleItemListBinding
import com.anshad.g_coaster.model.ItemsModel
import com.anshad.g_coaster.utils.BaseAdapter

class OutofStockAdapter(private val list: List<ItemsModel>,
                        val callBack: ItemClickListner
): BaseAdapter<OutofstockListitemBinding, ItemsModel>(list) {
    override val layoutId: Int = R.layout.outofstock_listitem

    override fun bind(binding: OutofstockListitemBinding, item: ItemsModel) {
        binding.apply {
            itemData = item
            binding.listner = callBack
            executePendingBindings()
        }
    }


}

interface ItemClickListner {
    fun onDataItemClicked(itemData: ItemsModel)
}