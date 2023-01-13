package com.anshad.g_coaster.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.anshad.basestructure.ui.BaseFragment
import com.anshad.g_coaster.R
import com.anshad.g_coaster.databinding.FragmentItemsBinding
import com.anshad.g_coaster.model.ItemsModel
import com.anshad.g_coaster.utils.ItemWithSwipeButtonClickCallBack
import com.anshad.g_coaster.utils.SwipeRevealLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ItemsFragment : BaseFragment<ItemsViewModel>(R.layout.fragment_items), ItemClickListner,
    ItemWithSwipeButtonClickCallBack<ItemsModel> {

    private var _binding: FragmentItemsBinding? = null
    private val binding get() = _binding!!

    override val viewModel: ItemsViewModel by viewModels()
    private val adapter = ItemsListAdapter(listOf(), this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentItemsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()


         viewModel.getItems()

        viewModel.itemsObserveListData.observe(viewLifecycleOwner) {
            it?.let {
                if(it.result?.size!! > 0){
                    adapter.updateData(it?.result!!)
                }

            }

        }

        binding.fabPlus.setOnClickListener {

            viewModel.navigateToAddFragment(null)
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {


               // adapter.updateData( adapter.data.filter { it.name?.contains(query.toString()) == true ||it.name?.contains(query.toString()) == true })

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // if query text is change in that case we
                // are filtering our adapter with
                // new text on below line.
              //  adapter.updateData( adapter.data.filter { it.name?.contains(newText.toString()) == true ||it.name?.contains(newText.toString()) == true })

                filterData(newText);
                return false
            }
        })

        val clearButton: ImageView =
            binding.search.findViewById(androidx.appcompat.R.id.search_close_btn)
        clearButton.setOnClickListener { v ->

            if(binding.search.getQuery().isEmpty()) {
                binding.search.setIconified(true);
            } else {

                // Do your task here
                binding.search.setQuery("", false);
                adapter.updateData(viewModel.itemsArray)
            }


        }

    }

    private fun filterData(newText: String?) {
        // creating a new array list to filter our data.
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<ItemsModel?> = ArrayList<ItemsModel?>()

        // running a for loop to compare elements.
        if(newText==""){
            adapter.updateData(viewModel.itemsArray)
        }

        // running a for loop to compare elements.
        for (item in adapter.data) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name?.contains(newText.toString(),true) == true || item.codename?.contains(newText.toString(),true) == true || item.color?.contains(newText.toString(),true) == true ) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.updateData(filteredlist as ArrayList<ItemsModel>)
        }

    }

    override fun onDataItemClicked(itemData: ItemsModel) {
        //
    }

    override fun onClick(item: ItemsModel) {
        //
    }

    override fun onMenu(item: ItemsModel, id: Int, swipeRevealLayout: SwipeRevealLayout) {
        when (id) {
            R.id.delete_btn -> deleteItem(item)
            R.id.edit_btn -> editItem(item)
        }
    }

    private fun deleteItem(item: ItemsModel) {
        viewModel.deleteItem(item)
    }

    private fun editItem(item: ItemsModel) {
        viewModel.navigateToAddFragment(item)
    }

}




