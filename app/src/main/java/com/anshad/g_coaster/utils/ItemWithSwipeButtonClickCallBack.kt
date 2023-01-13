package com.anshad.g_coaster.utils

interface ItemWithSwipeButtonClickCallBack<T>: ItemClickCallBack<T> {
    fun onMenu(item:T,id:Int,swipeRevealLayout: SwipeRevealLayout)
}