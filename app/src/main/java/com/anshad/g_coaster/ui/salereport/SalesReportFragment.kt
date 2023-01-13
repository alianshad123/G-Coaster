package com.anshad.g_coaster.ui.salereport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.anshad.basestructure.ui.BaseFragment
import com.anshad.g_coaster.R
import com.anshad.g_coaster.databinding.FragmentSalesReportBinding
import com.anshad.g_coaster.model.SalesReport

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalesReportFragment :  BaseFragment<SalesReportViewModel>(R.layout.fragment_sales_report),
    ItemClickListner {


    private var _binding: FragmentSalesReportBinding? = null
    private val binding get() = _binding!!

    override val viewModel: SalesReportViewModel by viewModels()
    private val adapter = SalesReportAdapter(listOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSalesReportBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSalesReport()

        viewModel.salesReportData.observe(viewLifecycleOwner) {
            it?.let {
                if (it.result?.size!! > 0) {
                    adapter.updateData(it?.result!!)
                }

            }

        }
    }




    override fun onDataItemClicked(salesReport: SalesReport) {
        viewModel.showDetailedReport(salesReport)
    }

}