package com.anshad.g_coaster.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Database(
    entities = [Cart::class],
    version = 1,
    exportSchema = false
)

abstract class CartDatabase: RoomDatabase() {
    abstract fun getCartDao(): CartDao

    companion object {
        const val DB_NAME = "cart_database.db"
        @Volatile private var instance: CartDatabase? = null
        private val LOCK = Any()

       /* operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: (context).also {
                instance = it
            }
        }*/

        /*private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CartDatabase::class.java,
            DB_NAME
        ).build()*/
    }
}