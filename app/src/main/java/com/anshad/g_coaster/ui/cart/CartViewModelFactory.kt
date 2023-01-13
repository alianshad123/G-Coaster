package com.anshad.g_coaster.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anshad.g_coaster.data.repositories.CartRepository

class CartViewModelFactory(
    private val repository: CartRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            val constructor = modelClass.getDeclaredConstructor(CartRepository::class.java)
            return constructor.newInstance(repository)
        } catch (e: Exception) {
            //Log.e(e.message.toString())
        }
        return super.create(modelClass)
    }
}