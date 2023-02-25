package com.anshad.g_coaster.ui.saleitems.dialogs

import android.text.Editable
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anshad.basestructure.ui.BaseViewModel
import com.anshad.g_coaster.App
import com.anshad.g_coaster.model.ItemsModel
import com.anshad.g_coaster.model.ItemsModelData

class QuantityDialogViewModel: BaseViewModel() {

    var quantity: String?=null
    var code: String?=null
    var name: String?=null
    var rate: String?=null
    
    private var _quantityValue = MutableLiveData<String?>()
    val quantityValue: LiveData<String?>?
        get() = _quantityValue

    private var _rateValue = MutableLiveData<String?>()
    val rateValue: LiveData<String?>?
        get() = _rateValue

    init {
        _quantityValue.value="1"
        _rateValue.value="0"

    }

    var addToCart: MutableLiveData<ItemsModel?> = MutableLiveData()
    var isCancelled: MutableLiveData<Boolean> = MutableLiveData()
    var increment: MutableLiveData<Boolean> = MutableLiveData()
    var decrement: MutableLiveData<Boolean> = MutableLiveData()

    fun setQuantityValue(s: Editable){
        _quantityValue.value = s.toString()
    }

    fun setRateValue(s: Editable){
        _rateValue.value = s.toString()
    }

    fun onSubmit(){
        if((_quantityValue.value?.toInt() ?: 0) <= 0 ){
            addToCart.postValue(null)
        }else {
            if(!_rateValue.value.isNullOrBlank()){

                if((_rateValue.value?.toDouble() ?: 0.0) > 0.0) {
                    val qty = quantity?.toInt()?.minus(_quantityValue.value?.toInt() ?: 0)
                    val rate= _rateValue.value
                    if (qty != null) {
                        if (qty >= 0) {


                            val itemsModel=ItemsModel()
                            itemsModel.quantity=_quantityValue.value?.toInt()
                            itemsModel.sellingprize=rate?.toDouble()
                            addToCart.postValue(itemsModel)
                            closeDialog()
                        } else {
                            Toast.makeText(
                                App.instance.applicationContext,
                                "Out of stock",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }else{
                    val qty = quantity?.toInt()?.minus(_quantityValue.value?.toInt() ?: 0)
                    if (qty != null) {
                        if (qty >= 0) {

                            val itemsModel=ItemsModel()
                            itemsModel.quantity=_quantityValue.value?.toInt()
                            itemsModel.sellingprize=rate?.toDouble()
                            addToCart.postValue(itemsModel)
                            closeDialog()
                        } else {
                            Toast.makeText(
                                App.instance.applicationContext,
                                "Out of stock",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }

            }else{

                val qty = quantity?.toInt()?.minus(_quantityValue.value?.toInt() ?: 0)
                if (qty != null) {
                    if (qty >= 0) {

                        val itemsModel=ItemsModel()
                        itemsModel.quantity=_quantityValue.value?.toInt()
                        itemsModel.sellingprize=rate?.toDouble()
                        addToCart.postValue(itemsModel)
                        closeDialog()
                    } else {
                        Toast.makeText(
                            App.instance.applicationContext,
                            "Out of stock",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }



        }

    }

    private fun closeDialog() {
        isCancelled.postValue(true)
    }

    fun onCancel(){
        closeDialog()
    }

  /*  fun incrementQty(){
        val value: Int? =_quantityValue?.value?.toInt()
        if (value != null) {
          _quantityValue.value= value.toString()
        }
    }
    fun decrementQty(){
        val value: Int? =quantityValue?.value?.toInt()
        if (value != null) {
            _quantityValue.postValue((value-1).toString())
        }
    }*/



}