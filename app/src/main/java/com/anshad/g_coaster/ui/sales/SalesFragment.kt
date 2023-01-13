package com.anshad.g_coaster.ui.sales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.anshad.basestructure.model.LoadingMessageData
import com.anshad.basestructure.ui.BaseFragment
import com.anshad.basestructure.utils.EventObserver
import com.anshad.g_coaster.R
import com.anshad.g_coaster.databinding.FragmentSalesBinding
import com.anshad.g_coaster.model.SoldItems

import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SalesFragment :  BaseFragment<SalesViewModel>(R.layout.fragment_sales),
    ItemClickListner {

    private var _binding: FragmentSalesBinding? = null
    private val binding get() = _binding!!

    override val viewModel: SalesViewModel by viewModels()
    private val adapter = SalesAdapter(listOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.dateKey = arguments?.getString("dateKey") as String
        viewModel.saleId = arguments?.getLong("saleId") as Long
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSalesBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*if((viewModel.dateKey?.length ?: 0) > 5){
            viewModel.dateKey?.let {
                viewModel.date=viewModel.dateKey
                viewModel.getSales(it)
            }

        }else {
            val format = SimpleDateFormat("yyyy-MM-dd")
            viewModel.date=format.format(Date())
            viewModel.getSales(format.format(Date()))
        }*/

        if(viewModel.saleId!=null){
            viewModel.getSales(viewModel.saleId)
        }



        viewModel.salesData.observe(viewLifecycleOwner) {
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


    override fun onDataItemClicked(itemData: SoldItems) {
      // // TODO("Not yet implemented")
    }


}