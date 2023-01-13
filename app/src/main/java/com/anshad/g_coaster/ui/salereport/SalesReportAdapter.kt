package com.anshad.g_coaster.ui.salereport

import com.anshad.g_coaster.R
import com.anshad.g_coaster.databinding.OutofstockListitemBinding
import com.anshad.g_coaster.databinding.SalesreportListItemBinding
import com.anshad.g_coaster.model.ItemsModel
import com.anshad.g_coaster.model.SalesReport
import com.anshad.g_coaster.utils.BaseAdapter

class SalesReportAdapter(private val list: List<SalesReport>,
                         val callBack: ItemClickListner
): BaseAdapter<SalesreportListItemBinding, SalesReport>(list) {
    override val layoutId: Int = R.layout.salesreport_list_item

    override fun bind(binding: SalesreportListItemBinding, item: SalesReport) {
        binding.apply {
            salesReport = item
            binding.listner = callBack
            executePendingBindings()
        }
    }


}

interface ItemClickListner {
    fun onDataItemClicked(salesReport: SalesReport)
}