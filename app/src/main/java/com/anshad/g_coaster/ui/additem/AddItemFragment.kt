package com.anshad.g_coaster.ui.additem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anshad.basestructure.ui.BaseFragment
import com.anshad.g_coaster.R
import com.anshad.g_coaster.databinding.FragmentAddItemBinding
import com.anshad.g_coaster.model.ItemsModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddItemFragment : BaseFragment<AddItemViewModel>(R.layout.fragment_add_item) {
    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    override val viewModel: AddItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.isEdit = arguments?.getBoolean("isEdit") as Boolean

        var itemData = arguments?.getSerializable("item")
        itemData?.let {
            viewModel.itemData = it as ItemsModel
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()


        viewModel.itemData.let {
            if(it?.id!=null){
                viewModel.name.postValue(it.name)
                viewModel.code.postValue(it.codename)
                viewModel.costPrize.postValue(it.costprize.toString())
                viewModel.sellingPrize.postValue(it.sellingprize.toString())
                viewModel.quantity.postValue(it.quantity.toString())
                viewModel.size.postValue(it.size.toString())
                viewModel.color.postValue(it.color.toString())

            }

        }

        if(viewModel.isEdit){
            binding.btnAdditem.text="UPDATE"
            binding.tvChoosemultisize.visibility=View.INVISIBLE
        }

        binding.btnAdditem.setOnClickListener {

            if(binding.btnAdditem.text=="UPDATE"){
                viewModel.updateItemData(viewModel.itemData?.id)
            }else {
                viewModel.addItemData()
            }
        }



        viewModel.itemAdded.observe(viewLifecycleOwner, Observer {
            if(it){
                Toast.makeText(requireContext(),"Item Added Successfully",Toast.LENGTH_SHORT).show()
                clearAllText()
            }

        })

        binding.chipsGroup.forEach { child ->
            (child as? Chip)?.setOnCheckedChangeListener { buttonView, isChecked ->
               viewModel.sizeArray.add(buttonView.text.toString()
               )

            }
        }

        binding.tvChoosemultisize.setOnClickListener {
            if(binding.tvChoosemultisize.tag=="hide"){
                binding.chipsGroup.visibility=View.VISIBLE
                binding.tvChoosemultisize.tag="show"
            }else{
                binding.chipsGroup.visibility=View.GONE
                binding.tvChoosemultisize.tag="hide"


            }

        }

    }

    private fun clearAllText() {
        binding.name.text?.clear()
        binding.codename.text?.clear()
        binding.costprize.text?.clear()
        binding.sellingprize.text?.clear()
        binding.quantity.text?.clear()
        binding.size.text?.clear()
        binding.color.text?.clear()
        binding.chipsGroup.clearCheck()
        viewModel.sizeArray.clear()

        binding.chipsGroup.visibility=View.GONE
        binding.tvChoosemultisize.tag="hide"
        binding.tvChoosemultisize.visibility=View.VISIBLE
        binding.btnAdditem.text="ADD ITEM"

    }


}