package com.anshad.g_coaster.ui.saleitems

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.anshad.basestructure.model.LoadingMessageData
import com.anshad.basestructure.ui.BaseFragment
import com.anshad.basestructure.utils.EventObserver
import com.anshad.g_coaster.R
import com.anshad.g_coaster.constants.enums.QuantityEvents
import com.anshad.g_coaster.databinding.FragmentSaleItemsBinding
import com.anshad.g_coaster.db.Cart
import com.anshad.g_coaster.model.ItemsModel
import com.anshad.g_coaster.ui.saleitems.dialogs.QuantityDialogFragment
import com.anshad.g_coaster.ui.saleitems.dialogs.QuantityDialogListner
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SaleItemsFragment : BaseFragment<SaleItemsViewModel>(R.layout.fragment_sale_items),
    ItemClickListner {
    private var _binding: FragmentSaleItemsBinding? = null
    private val binding get() = _binding!!

    override val viewModel: SaleItemsViewModel by viewModels()
    private val adapter = SaleItemsAdapter(listOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSaleItemsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        //val menuHost: MenuHost =  requireActivity()
        viewModel.getItems()

        viewModel.itemsObserveListData.observe(viewLifecycleOwner) {
            it?.let {
                if (it.result?.size!! > 0) {
                    adapter.updateData(it?.result!!)
                }

            }

        }

        viewModel.itemData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null) {
                    Toast.makeText(requireContext(),"Item added to cart",Toast.LENGTH_SHORT).show()
                    val cartitem = Cart(
                        id = null,
                        itemId = it.id,
                        name = it.name,
                        codename = it.codename,
                        costprize = it.costprize,
                        sellingprize = it.sellingprize,
                        quantity = it.quantity,
                        size = it.size,
                        color = it.color,

                        )
                    CoroutineScope(Dispatchers.Main).launch {
                        viewModel.insertCart(cartitem).also {
                            viewModel.getItems()
                        }
                    }
                }

            }
        }
        )


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

                viewModel.filterData(newText);
                return false
            }
        })

        val clearButton: ImageView =
            binding.search.findViewById(androidx.appcompat.R.id.search_close_btn)
        clearButton.setOnClickListener { v ->

            if (binding.search.getQuery().isEmpty()) {
                binding.search.setIconified(true);
            } else {

                // Do your task here
                binding.search.setQuery("", false);
               // adapter.updateData(viewModel.itemsArray)
                viewModel.getItems()
            }


        }

     // onCreateOptionsMenu()

      /*  menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_cart -> {
                        viewModel.navigateToCart()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)*/

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

    private fun filterData(newText: String?) {
        // creating a new array list to filter our data.
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<ItemsModel?> = ArrayList<ItemsModel?>()

        // running a for loop to compare elements.
        // running a for loop to compare elements.
        if(newText==""){
            adapter.updateData(viewModel.itemsArray)
        }

        // running a for loop to compare elements.
        for (item in adapter.data) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name?.contains(newText.toString(),true) == true || item.codename?.contains(newText.toString(),true) == true || item.color?.contains(
                    newText.toString(),true
                ) == true
            ) {
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
        val dialog = QuantityDialogFragment().newInstance(itemData)
        dialog?.isCancelable = true
        dialog?.eventListner = object : QuantityDialogListner {
            override fun onDialogEvents(events: QuantityEvents, item: Any) {
                val item = item as ItemsModel
                viewModel.updateItem(itemData, item)

            }

        }
        dialog?.show(childFragmentManager, "quantity")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cart -> {
                viewModel.navigateToCart()
                true
            }
        }
        return false
    }


}