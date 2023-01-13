package com.anshad.g_coaster.ui.bills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anshad.basestructure.model.LoadingMessageData
import com.anshad.basestructure.ui.BaseFragment
import com.anshad.basestructure.utils.EventObserver
import com.anshad.g_coaster.R
import com.anshad.g_coaster.databinding.FragmentBillsBinding
import com.anshad.g_coaster.model.BillList
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class BillsFragment :  BaseFragment<BillsViewModel>(R.layout.fragment_bills),
    ItemClickListner {

    private var _binding: FragmentBillsBinding? = null
    private val binding get() = _binding!!

    override val viewModel: BillsViewModel by viewModels()
    private val adapter = BillsListAdapter(listOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.dateKey = arguments?.getString("dateKey") as String

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBillsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      viewModel?.date?.let {
          viewModel.getBills(it)
      }



        viewModel.billsData.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                if(it.result?.size!! >0){
                    adapter.updateData(it?.result!!)
                }else{

                }

            }else{

            }
        })
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

    override fun onDataItemClicked(billsData: BillList) {
        viewModel.navigateToSoldItems(viewModel.date,billsData.id)
    }


}