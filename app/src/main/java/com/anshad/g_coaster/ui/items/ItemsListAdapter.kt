package com.anshad.g_coaster.ui.items

import com.anshad.g_coaster.R
import com.anshad.g_coaster.databinding.ItemsListBinding
import com.anshad.g_coaster.model.ItemsModel
import com.anshad.g_coaster.utils.BaseAdapter
import com.anshad.g_coaster.utils.ItemWithSwipeButtonClickCallBack

class ItemsListAdapter(private val list: List<ItemsModel>,
                       val callBack: ItemWithSwipeButtonClickCallBack<ItemsModel>
): BaseAdapter<ItemsListBinding, ItemsModel>(list) {
    override val layoutId: Int = R.layout.items_list

    override fun bind(binding: ItemsListBinding, item: ItemsModel) {
        binding.apply {
            itemData = item
            binding.callback = callBack
            executePendingBindings()
        }
    }


}

interface ItemClickListner {
    fun onDataItemClicked(itemData: ItemsModel)
}