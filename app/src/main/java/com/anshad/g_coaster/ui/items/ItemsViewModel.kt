package com.anshad.g_coaster.ui.items

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.anshad.basestructure.ui.BaseViewModel
import com.anshad.g_coaster.App
import com.anshad.g_coaster.R
import com.anshad.g_coaster.data.repositories.ItemsRepository
import com.anshad.g_coaster.data.repositories.PreferenceProvider
import com.anshad.g_coaster.model.AddItemModel
import com.anshad.g_coaster.model.ItemsModel
import com.anshad.g_coaster.model.ItemsModelData
import com.anshad.g_coaster.model.PageLimit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val repository: ItemsRepository
) : BaseViewModel() {


    private val pageLimit: Int=10
    val _itemsObserveList = MutableLiveData<ItemsModelData?>()
    val itemsObserveListData: MutableLiveData<ItemsModelData?> = _itemsObserveList

    var itemsArray:ArrayList<ItemsModel> = ArrayList<ItemsModel>()

    fun getItems(){
        showLoading()
        repository.getItems(pageLimit).subscribe({ apiResult ->
            hideLoading()
            if (apiResult.isSuccess) {

                _itemsObserveList.postValue(apiResult.data)
                apiResult.data?.result?.forEach {
                    itemsArray.add(it)
                }
            } else {
                _itemsObserveList.postValue(null)


            }
        }, {
            hideLoading()

        })
    }

    fun navigateToAddFragment(item: ItemsModel?) {
        if(item!=null){

            val bundle = Bundle()
            bundle.putBoolean("isEdit", true)
            bundle.putSerializable("item",item)
            navigate(R.id.action_itemsFragment_to_addItemFragment,bundle)

        }else{
            val bundle = Bundle()
            bundle.putBoolean("isEdit", false)
            navigate(R.id.action_itemsFragment_to_addItemFragment,bundle)
        }

    }

    fun deleteItem(item: ItemsModel) {
            showLoading()
            repository.deleteItem(
                AddItemModel(
                    id=item.id
                )
            ).subscribe({ result ->
                hideLoading()
                if (result.isSuccess) {
                    Toast.makeText(App.instance.applicationContext,"Item Deleted",Toast.LENGTH_SHORT).show()

                   getItems()


                } else {


                }
            }, {
                hideLoading()

            })
        }




}