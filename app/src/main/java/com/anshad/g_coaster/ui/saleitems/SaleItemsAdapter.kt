package com.anshad.g_coaster.ui.saleitems

import com.anshad.g_coaster.R
import com.anshad.g_coaster.databinding.ItemsListBinding
import com.anshad.g_coaster.databinding.SaleItemListBinding
import com.anshad.g_coaster.model.ItemsModel
import com.anshad.g_coaster.utils.BaseAdapter


class SaleItemsAdapter(private val list: List<ItemsModel>,
                       val callBack: ItemClickListner
): BaseAdapter<SaleItemListBinding, ItemsModel>(list) {
    override val layoutId: Int = R.layout.sale_item_list

    override fun bind(binding: SaleItemListBinding, item: ItemsModel) {
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