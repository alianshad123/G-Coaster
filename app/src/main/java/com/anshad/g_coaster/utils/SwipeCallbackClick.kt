package com.anshad.g_coaster.utils

interface SwipeCallbackClick <T>: ItemClickCallBack<T> {
    fun onMenu(item:T,id:Int,swipeRevealLayout: SwipeRevealLayout)
}