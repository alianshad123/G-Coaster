package com.anshad.g_coaster.ui.outofstocks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.anshad.basestructure.model.LoadingMessageData
import com.anshad.basestructure.ui.BaseFragment
import com.anshad.basestructure.utils.EventObserver
import com.anshad.g_coaster.R
import com.anshad.g_coaster.databinding.FragmentOutofStockBinding
import com.anshad.g_coaster.databinding.FragmentSaleItemsBinding
import com.anshad.g_coaster.databinding.FragmentSalesReportBinding
import com.anshad.g_coaster.model.ItemsModel
import com.anshad.g_coaster.ui.saleitems.SaleItemsAdapter
import com.anshad.g_coaster.ui.saleitems.SaleItemsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OutofStockFragment : BaseFragment<OutofStockViewModel>(R.layout.fragment_outof_stock),
    ItemClickListner {
    private var _binding: FragmentOutofStockBinding? = null
    private val binding get() = _binding!!

    override val viewModel: OutofStockViewModel by viewModels()
    private val adapter = OutofStockAdapter(listOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOutofStockBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getOutofStocks()

        viewModel.itemsObserveListData.observe(viewLifecycleOwner) {
            it?.let {
                if (it.result?.size!! > 0) {
                    adapter.updateData(it?.result!!)
                }

            }

        }
        viewModel.loading_.observe(viewLifecycleOwner, EventObserver {
            _onLoadingMessage(it)
        })
    }

    private fun _onLoadingMessage(messageData: LoadingMessageData) {
        if (messageData.context == null) {
            messageData.context = requireContext()
        }
        binding.isLoading = messageData.isLoading
        binding.message = messageData.getMessage()

    }



    override fun onDataItemClicked(itemData: ItemsModel) {
        //viewModel.navigateToAddFragment(itemData)
    }


}