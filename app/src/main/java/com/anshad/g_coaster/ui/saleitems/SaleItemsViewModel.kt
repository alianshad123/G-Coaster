package com.anshad.g_coaster.ui.saleitems

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anshad.basestructure.model.Event
import com.anshad.basestructure.model.LoadingMessageData
import com.anshad.basestructure.ui.BaseViewModel
import com.anshad.g_coaster.App
import com.anshad.g_coaster.R
import com.anshad.g_coaster.data.repositories.CartRepository
import com.anshad.g_coaster.data.repositories.ItemsRepository
import com.anshad.g_coaster.data.repositories.PreferenceProvider
import com.anshad.g_coaster.data.repositories.SaleItemRepository
import com.anshad.g_coaster.db.Cart
import com.anshad.g_coaster.db.Items
import com.anshad.g_coaster.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaleItemsViewModel @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val repository: ItemsRepository,
    private val salerepository: SaleItemRepository,
    private val cartRepository: CartRepository
) : BaseViewModel() {


    val _itemsObserveList = MutableLiveData<ItemsModelData?>()
    val itemsObserveListData: MutableLiveData<ItemsModelData?> = _itemsObserveList

    var itemsArray:ArrayList<ItemsModel> = ArrayList<ItemsModel>()

    val itemUpdated = MutableLiveData<Boolean>()

    val itemData = MutableLiveData<ItemsModel>()

    var cartDataList = ArrayList<CartModel>()
    var cartDataArrayList = ArrayList<CartItemModel>()

    private val loadingLiveData = MutableLiveData<Event<LoadingMessageData>>()
    val loading_: LiveData<Event<LoadingMessageData>> = loadingLiveData

    fun showLoading_(message: LoadingMessageData) {
        loadingLiveData.value = Event(message)
    }

    fun showLoading_(@StringRes message: Int = R.string.loading) {
        val messageData = LoadingMessageData()
        messageData.isLoading = true
        messageData.messageRes = message
        showLoading_(messageData)
    }

    fun hideLoading_() {
        val messageData = LoadingMessageData()
        messageData.isLoading = false
        showLoading_(messageData)
    }


    suspend fun insertCart(cart: Cart) = cartRepository.insertCart(cart)
    suspend fun insertItems(items: List<Items>) = salerepository.insertItems(items)
    suspend fun getAllItems() = salerepository.getAllItems()

    fun getItems(){
        showLoading_()
        repository.getItems().subscribe({ apiResult ->
            hideLoading_()
            if (apiResult.isSuccess) {
                 val arrayList:ArrayList<ItemsModel> =apiResult.data?.result?:ArrayList()

                val itemsData:ArrayList<Items> = ArrayList()
                    arrayList.forEach {
                        val items = Items()
                        items.id = it.id
                        items.name = it.name
                        items.codename = it.codename
                        items.costprize = it.costprize
                        items.sellingprize = it.sellingprize
                        items.quantity = it.quantity
                        items.size = it.size
                        items.isDeleted = it.isDeleted
                        items.color = it.color
                        itemsData.add(items)

                    }


                CoroutineScope(Dispatchers.Main).launch {
                    insertItems(itemsData)
                }
             //   _itemsObserveList.postValue(apiResult.data)
                apiResult.data?.result?.forEach {
                    itemsArray.add(it)
                }
            } else {
                _itemsObserveList.postValue(null)


            }
        }, {


            hideLoading_()

        })
    }

    fun updateItem(item: ItemsModel, itemD: ItemsModel) {
        showLoading_()
        val qty=item.quantity?.minus(itemD.quantity?.toInt()?:0)
        salerepository.updateItem(
            AddItemModel(
                id=item.id,
                name = item.name,
                codename =item.codename,
                costprize = item.costprize,
                sellingprize = item.sellingprize,
                quantity = qty,
                size = item.size,
                color =item.color
            )
        ).subscribe({ result ->
            hideLoading_()
            if (result.isSuccess) {

                itemData.postValue(item.apply {
                    this.quantity=itemD.quantity?.toInt()
                    this.sellingprize=itemD.sellingprize
                })


            } else {


            }
        }, {
            Toast.makeText(App.instance.applicationContext,"No Internet",Toast.LENGTH_SHORT).show()

            hideLoading_()

        })
    }

    fun navigateToCart() {

        navigate(R.id.action_saleItemsFragment_to_cartFragment)

    }

    fun filterData(newText: String?) {
        if((newText?.length ?: 0) <= 0){
            getItems()
            return
        }
        showLoading_()
        repository.searchItem(SearchItem(search = newText)).subscribe({ apiResult ->
            hideLoading_()
            if (apiResult.isSuccess) {

                _itemsObserveList.postValue(apiResult.data)
                apiResult.data?.result?.forEach {
                    itemsArray.add(it)
                }
            } else {
                _itemsObserveList.postValue(null)


            }
        }, {
            hideLoading_()
            Toast.makeText(App.instance.applicationContext,"No Internet",Toast.LENGTH_SHORT).show()

        })
    }
}