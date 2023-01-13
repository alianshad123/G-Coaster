package com.anshad.g_coaster.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //if some data is same/conflict, it'll be replace with new data.
    suspend fun insertCart(cart: Cart)

    @Update
    suspend fun updateCart(cart: Cart)

    @Delete
    suspend fun deleteCart(cart: Cart)

    @Query("SELECT * FROM cart_table ")
    fun getAllCarts(): LiveData<List<Cart>>
    // why not use suspend ? because Room does not support LiveData with suspended functions.
    // LiveData already works on a background thread and should be used directly without using coroutines

    @Query("DELETE FROM cart_table")
    suspend fun clearCart()

    @Query("DELETE FROM cart_table WHERE id = :id") //you can use this too, for delete note by id.
    suspend fun deleteCartById(id: Int)

}