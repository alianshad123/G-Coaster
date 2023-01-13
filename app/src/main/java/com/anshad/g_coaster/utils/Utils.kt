package com.anshad.g_coaster.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.anshad.g_coaster.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class Utils {

    companion object {


         fun getDateTime(): String {

             var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
             val dateString: String = sdf.format(Date())
             return dateString;
         }

        fun getAppPath(context: Context): String {
             val filepath = "MyFileStorage"

            val dir:File= File(context.getExternalFilesDir(filepath),"sale_pdf.pdf")  //File(android.os.Environment.getExternalStorageDirectory().toString()+File.separator
                    /*+context.resources.getString(R.string.app_name)
                    +File.separator)*/
            if(!dir.exists()){
                dir.mkdir()
                return dir.path +File.separator
            }
            return dir.path +File.separator
        }

        fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
            convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

        private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
            if (sourceDrawable == null) {
                return null
            }
            return if (sourceDrawable is BitmapDrawable) {
                sourceDrawable.bitmap
            } else {
// copying drawable object to not manipulate on the same reference
                val constantState = sourceDrawable.constantState ?: return null
                val drawable = constantState.newDrawable().mutate()
                val bitmap: Bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth, drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
                bitmap
            }
        }
    }


}


