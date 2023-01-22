package com.anshad.g_coaster.ui.saleitems

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anshad.basestructure.model.Event
import com.anshad.basestructure.model.LoadingMessageData
import com.anshad.basestructure.ui.BaseViewModel
import com.anshad.g_coaster.R
import com.anshad.g_coaster.data.repositories.CartRepository
import com.anshad.g_coaster.data.repositories.ItemsRepository
import com.anshad.g_coaster.data.repositories.PreferenceProvider
import com.anshad.g_coaster.data.repositories.SaleItemRepository
import com.anshad.g_coaster.db.Cart
import com.anshad.g_coaster.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun getItems(){
        showLoading_()
        repository.getItems().subscribe({ apiResult ->
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

        })
    }

    fun updateItem(item: ItemsModel, quantity: String) {
        showLoading_()
        val qty=item.quantity?.minus(quantity.toInt())
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
                    this.quantity=quantity.toInt()
                })


            } else {


            }
        }, {
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

        })
    }
}