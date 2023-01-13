package com.anshad.g_coaster.ui.bills

import com.anshad.g_coaster.R
import com.anshad.g_coaster.databinding.BillsListitemBinding
import com.anshad.g_coaster.model.BillList
import com.anshad.g_coaster.utils.BaseAdapter

class BillsListAdapter (private val list: List<BillList>,
                        val callBack: ItemClickListner
): BaseAdapter<BillsListitemBinding, BillList>(list) {
    override val layoutId: Int = R.layout.bills_listitem

    override fun bind(binding: BillsListitemBinding, item: BillList) {
        binding.apply {
            billsData = item
            binding.listner = callBack
            executePendingBindings()
        }
    }


}

interface ItemClickListner {
    fun onDataItemClicked(billsData: BillList)
}