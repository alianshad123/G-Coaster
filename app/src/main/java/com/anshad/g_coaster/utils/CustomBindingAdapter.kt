package com.anshad.g_coaster.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter


class CustomBindingAdapter {

    companion object {

        @BindingAdapter("backgroundTint")
        @JvmStatic
        fun setBackgroundImageTint(view: View, color:Int)
        {
            view.setBackgroundColor(color)
        }

        @BindingAdapter("text_color")
        @JvmStatic
        fun setTextColor(view: TextView, color: Int) {
            view.setTextColor(color)
        }

    }
}