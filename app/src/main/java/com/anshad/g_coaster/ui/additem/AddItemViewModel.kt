package com.anshad.g_coaster.ui.additem

import androidx.lifecycle.MutableLiveData
import com.anshad.basestructure.ui.BaseViewModel
import com.anshad.g_coaster.App
import com.anshad.g_coaster.R
import com.anshad.g_coaster.data.repositories.AddItemRepository
import com.anshad.g_coaster.model.AddItemModel
import com.anshad.g_coaster.model.ItemsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val repository: AddItemRepository,
) : BaseViewModel() {


    val name = MutableLiveData<String>()
    val nameError = MutableLiveData<String>()

    val code = MutableLiveData<String>()
    val codeError = MutableLiveData<String>()

    val color = MutableLiveData<String>()
    val colorError = MutableLiveData<String>()

    val costPrize = MutableLiveData<String>()
    val costPrizeError = MutableLiveData<String>()

    val sellingPrize = MutableLiveData<String>()
    val sellingPrizeError = MutableLiveData<String>()

    val quantity = MutableLiveData<String>()
    val quantityError = MutableLiveData<String>()

    val size = MutableLiveData<String>()
    val sizeError = MutableLiveData<String>()

    val itemAdded = MutableLiveData<Boolean>()

    val sizeArray = ArrayList<String>()


    var itemData: ItemsModel? = ItemsModel()
    var isEdit: Boolean = false



    fun addItemData() {

        if(sizeArray.size>0){

            val arrayItems=ArrayList<AddItemModel>()

            if(validateItemData( name.value,
                    code.value,
                    costPrize.value,
                    sellingPrize.value,
                    quantity.value))

                sizeArray.distinct().forEach {

                arrayItems.add(
                    AddItemModel(
                        name = name.value,
                        codename = code.value,
                        costprize = costPrize.value?.toDouble(),
                        sellingprize = sellingPrize.value?.toDouble(),
                        quantity = quantity.value?.toInt(),
                        size = it.toInt(),
                        isDeleted = 0,
                        color = color.value?.toString()
            )
                )
          }
            addMultiItem(arrayItems)

        }else {


            if (validateData(
                    name.value,
                    code.value,
                    costPrize.value,
                    sellingPrize.value,
                    quantity.value,
                    size.value
                )
            ) {


                addItem()

            }
        }
    }

    private fun validateData(
        name: String?,
        code: String?,
        costPrize: String?,
        sellingPrize: String?,
        quantity: String?,
        size: String?
    ): Boolean {
        var res = true
       /* if (name.isNullOrBlank()) {
            res = false
            nameError.value =
                App.instance.getString(R.string.field_missing)
        }

        if (code.isNullOrBlank()) {
            res = false
            codeError.value =
                App.instance.getString(R.string.field_missing)
        }*/

        if (costPrize.isNullOrBlank()) {
            res = false
            costPrizeError.value =
                App.instance.getString(R.string.field_missing)
        }

        if (sellingPrize.isNullOrBlank()) {
            res = false
            sellingPrizeError.value =
                App.instance.getString(R.string.field_missing)
        }

        if (quantity.isNullOrBlank()) {
            res = false
            quantityError.value =
                App.instance.getString(R.string.field_missing)
        }

        if (size.isNullOrBlank()) {
            res = false
            sizeError.value =
                App.instance.getString(R.string.field_missing)
        }

        return res
    }

    private fun validateItemData(
        name: String?,
        code: String?,
        costPrize: String?,
        sellingPrize: String?,
        quantity: String?
    ): Boolean {
        var res = true
       /* if (name.isNullOrBlank()) {
            res = false
            nameError.value =
                App.instance.getString(R.string.field_missing)
        }

        if (code.isNullOrBlank()) {
            res = false
            codeError.value =
                App.instance.getString(R.string.field_missing)
        }*/

        if (costPrize.isNullOrBlank()) {
            res = false
            costPrizeError.value =
                App.instance.getString(R.string.field_missing)
        }

        if (sellingPrize.isNullOrBlank()) {
            res = false
            sellingPrizeError.value =
                App.instance.getString(R.string.field_missing)
        }

        if (quantity.isNullOrBlank()) {
            res = false
            quantityError.value =
                App.instance.getString(R.string.field_missing)
        }



        return res
    }

    fun addMultiItem(itemsData: ArrayList<AddItemModel>) {
        showLoading()
        repository.additemsArray(
            itemsData
        ).subscribe({ result ->
            hideLoading()
            if (result.isSuccess) {

                itemAdded.postValue(true)

            } else {

            }
        }, {
            hideLoading()

        })
    }


    fun addItem() {

        val arrayItems=ArrayList<AddItemModel>()
        arrayItems.add(AddItemModel(
            name = name.value,
            codename = code.value,
            costprize = costPrize.value?.toDouble(),
            sellingprize = sellingPrize.value?.toDouble(),
            quantity = quantity.value?.toInt(),
            size = size.value?.toInt(),
            isDeleted = 0,
            color=color.value
        ))
        showLoading()
        repository.additemsArray(
            arrayItems
        ).subscribe({ result ->
            hideLoading()
            if (result.isSuccess) {

                itemAdded.postValue(true)


            } else {


            }
        }, {
            hideLoading()

        })
    }

    fun updateItemData(id: Int?) {
        if (validateData(
                name.value,
                code.value,
                costPrize.value,
                sellingPrize.value,
                quantity.value,
                size.value
            )
        ) {
            updateItem(id)

        }
    }

    private fun updateItem(id: Int?) {
        showLoading()
        repository.updateitems(
            AddItemModel(
                id=id,
                name = name.value,
                codename = code.value,
                costprize = costPrize.value?.toDouble(),
                sellingprize = sellingPrize.value?.toDouble(),
                quantity = quantity.value?.toInt(),
                size = size.value?.toInt(),
                color =color.value
            )
        ).subscribe({ result ->
            hideLoading()
            if (result.isSuccess) {

                itemAdded.postValue(true)


            } else {


            }
        }, {
            hideLoading()

        })
    }


}