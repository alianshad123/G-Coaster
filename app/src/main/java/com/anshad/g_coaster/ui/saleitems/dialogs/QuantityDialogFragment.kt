package com.anshad.g_coaster.ui.saleitems.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.anshad.g_coaster.constants.enums.QuantityEvents
import com.anshad.g_coaster.databinding.FragmentQuantityDialogBinding
import com.anshad.g_coaster.model.ItemsModel


class QuantityDialogFragment : DialogFragment() {

    private var _binding: FragmentQuantityDialogBinding?=null
    private val binding get() = _binding!!
    private val viewModel by viewModels<QuantityDialogViewModel>()
    var eventListner: QuantityDialogListner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            viewModel.name = arguments?.getString("name","")
            viewModel.code = arguments?.getString("code","")
            viewModel.quantity = arguments?.getString("quantity","")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuantityDialogBinding.inflate(inflater, container, false)
        val view=  binding.root
        binding.eventListener=eventListner
        binding.viewModel=viewModel
        return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()

    }

    override fun onResume() {
        super.onResume()
        if (dialog == null) return
        val window = dialog!!.window ?: return
        val params = window.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        window.attributes = params
    }
    private fun setObservers() {
        viewModel.isCancelled.observe(viewLifecycleOwner) { aBoolean -> dismiss() }

        viewModel.addToCart.observe(viewLifecycleOwner) { _quantity ->
            if (_quantity!=null) {
                val quantity: String = _quantity.toString()
                eventListner?.onDialogEvents(QuantityEvents.POSITIVE,quantity);
            }else{
                binding.quantity.setError("Please enter the value grater than 0")
            }
        }
    }

    fun newInstance(itemData: ItemsModel): QuantityDialogFragment? {

        val dialog = QuantityDialogFragment()
        val args =Bundle()
        args.putString("name", itemData.name)
        args.putString("code", itemData.codename)
        args.putString("quantity", itemData.quantity.toString())
        dialog.arguments = args
        return dialog
    }



}