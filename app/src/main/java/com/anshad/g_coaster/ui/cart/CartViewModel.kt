package com.anshad.g_coaster.ui.cart

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anshad.basestructure.model.Event
import com.anshad.basestructure.model.LoadingMessageData
import com.anshad.basestructure.ui.BaseViewModel
import com.anshad.g_coaster.R
import com.anshad.g_coaster.data.repositories.AddItemRepository
import com.anshad.g_coaster.data.repositories.CartRepository
import com.anshad.g_coaster.data.repositories.ItemsRepository
import com.anshad.g_coaster.db.Cart
import com.anshad.g_coaster.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class CartViewModel  @Inject constructor(
    private val repository: CartRepository,
    private val itemrepository:ItemsRepository,
    private val addItemRepository: AddItemRepository
) : BaseViewModel() {


   /* suspend fun updateNote(note: Note) = repository.updateNote(note)

    suspend fun deleteNote(note: Note) = repository.deleteNote(note)

    suspend fun deleteNoteById(id: Int) = repository.deleteNoteById(id)

    suspend fun clearNote() = repository.clearNote()*/

    suspend fun getAllCartItems() = repository.getAllCartItems()

    suspend fun deleteCartItem(cart: Cart)= repository.deleteCartItem(cart)

    suspend fun clearCart() = repository.clearCart()

    var saleItems: List<SalesItemsModel>?=null
    var sale: SalesModel?=null
    var isClicked: Boolean=false
    val itemUpdated = MutableLiveData<Boolean>()
    val salesAdded = MutableLiveData<Boolean>()
    var sale_success: Boolean=false
    val totalAmount= MutableLiveData<Double>()
    val grantTotalAmount= MutableLiveData<Int>()
    val discountPercentage= MutableLiveData<String>()
    val discountAmount= MutableLiveData<Double>()
    val roundoff= MutableLiveData<String>()
    var cartList=ArrayList<Cart>()

    var grantTotalValue=0.0

    init {
        totalAmount.postValue(0.0)
        grantTotalAmount.postValue(0)
        discountAmount.postValue(0.0)
    }



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

    fun getItemData(itemId: Int?, quantity: Int?) {
        showLoading_()
        itemrepository.getItemById(  AddItemModel(
            id=itemId
        )).subscribe({ apiResult ->
            hideLoading_()
            if (apiResult.isSuccess) {

              updateItem(itemId,quantity,apiResult?.data)

            } else {



            }
        }, {
            hideLoading_()

        })

    }


    private fun updateItem(id: Int?, quantity: Int?, data: ItemsModel?) {
        val qty= quantity?.plus(data?.quantity?:0)
        showLoading_()
        addItemRepository.updateitems(
            AddItemModel(
                id=data?.id,
                name = data?.name,
                codename = data?.codename,
                costprize = data?.costprize,
                sellingprize = data?.sellingprize,
                quantity = qty,
                size = data?.size,
                color =data?.color
            )
        ).subscribe({ result ->
            hideLoading_()
            if (result.isSuccess) {

                itemUpdated.postValue(true)


            } else {


            }
        }, {
            hideLoading_()

        })
    }

    fun updateFields(cartItems: List<Cart>) {
        cartList= cartItems as ArrayList<Cart>
        var total:Double=0.0
        cartItems?.forEach {
            total= (total + (it.sellingprize!!* it.quantity!!))
        }
        totalAmount.postValue(total)
        total.roundToInt()
        grantTotalAmount.postValue(total.roundToInt())
        grantTotalValue=total
    }

    fun updateGrantTotal() {

        val grantTotal:Double=0.0
       // totalAmount.value-discountPercentage.

    }

    fun updateDiscountPer(dis: String?) {
        var grantTotal: Double = 0.0
        val discnt = dis?.toDouble()?.div(100)
        val discount = discnt?.times(totalAmount.value!!)
        discountAmount.postValue(discount?.toDouble())
        grantTotal = totalAmount.value?.minus(discount ?: 0.0)!!
        grantTotalValue=grantTotal
        grantTotalAmount.postValue(grantTotal.toInt())


    }

    fun updateDiscountRs(dis: String?) {
        var grantTotal: Double = 0.0
        val discnt = dis?.toDouble()
        grantTotal =grantTotalValue?.minus(discnt ?: 0.0)!!
        grantTotalAmount.postValue(grantTotal.toInt())


    }

    fun   updateSale() {
        showLoading_()

        val roundoffValue:Double= roundoff.value?.toDouble()?:0.0
        val discountPer:Int= discountAmount.value?.toInt()?:0
        if(sale!=null){
            sale=null
        }
        var qty=0
        cartList.forEach {
            qty= (qty + it.quantity!!)
        }
        sale = SalesModel(
            quantity = qty,
            billamount = totalAmount.value,
            grosstotal = grantTotalAmount.value?.toDouble(),
            discount = discountPer,
            roundoff = roundoffValue
        )
        repository.updateSale(
            sale!!
        ).subscribe({ result ->
            hideLoading_()
            if (result.isSuccess) {

                val saleId= result.data?.id
                updateSaleItems(saleId)

            } else {


            }
        }, {
            hideLoading_()

        })
    }

    private fun updateSaleItems(saleId: Int?) {
        if(saleItems!=null){
            saleItems=null
        }
      saleItems=   cartList.map {
            SalesItemsModel(itemId = it.itemId,
                saleId = saleId,
                name = it.name,
                codename = it.codename,
                sellingprize = it.sellingprize,
                quantity = it.quantity,
                size = it.size,
                color = it.color
            )
        }
        showLoading_()
        repository.updateSaleItems(
           saleItems!!
        ).subscribe({ result ->
            hideLoading_()
            if (result.isSuccess) {
                salesAdded.postValue(true)

            } else {


            }
        }, {
            hideLoading_()

        })


    }

    fun updateSalePrint(saleId: Int?, textToPrint: String) {
        showLoading_()


        repository.updateSalePrint(
            SalesUpdate(
                id = saleId,
                print = textToPrint

            )
        ).subscribe({ result ->
            hideLoading_()
            if (result.isSuccess) {

            } else {


            }
        }, {
            hideLoading_()

        })
    }


}