package com.anshad.g_coaster.ui.saleitems.dialogs

import android.text.Editable
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anshad.basestructure.ui.BaseViewModel
import com.anshad.g_coaster.App

class QuantityDialogViewModel: BaseViewModel() {

    var quantity: String?=null
    var code: String?=null
    var name: String?=null
    
    private var _quantityValue = MutableLiveData<String?>()
    val quantityValue: LiveData<String?>?
        get() = _quantityValue

    init {
        _quantityValue.value="1"
    }

    var addToCart: MutableLiveData<String?> = MutableLiveData()
    var isCancelled: MutableLiveData<Boolean> = MutableLiveData()
    var increment: MutableLiveData<Boolean> = MutableLiveData()
    var decrement: MutableLiveData<Boolean> = MutableLiveData()

    fun setQuantityValue(s: Editable){
        _quantityValue.value = s.toString()
    }

    fun onSubmit(){
        if((_quantityValue.value?.toInt() ?: 0) <= 0 ){
            addToCart.postValue(null)
        }else {
            val qty=quantity?.toInt()?.minus(_quantityValue.value?.toInt()?:0)
            if (qty != null) {
                if(qty>=0){
                    addToCart.postValue(_quantityValue.value)
                    closeDialog()
                }else{
                    Toast.makeText(App.instance.applicationContext,"Out of stock",Toast.LENGTH_SHORT).show()
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