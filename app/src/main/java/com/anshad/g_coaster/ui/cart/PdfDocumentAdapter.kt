package com.anshad.g_coaster.ui.cart

import android.content.Context
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import java.io.*

class PdfDocumentAdapter(context: Context, path: String) :PrintDocumentAdapter() {

    val context:Context?=context
    var path:String?=path

    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes?,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback?,
        extras: Bundle?
    ) {
       if(cancellationSignal?.isCanceled == true){
           callback?.onLayoutCancelled()
       }else{
           val builder:PrintDocumentInfo.Builder=PrintDocumentInfo.Builder("file_name")
           builder.setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
               .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
               .build()
           callback?.onLayoutFinished(builder.build(),!newAttributes?.equals(oldAttributes)!!)
       }
    }

    override fun onWrite(
        pages: Array<out PageRange>?,
        destination: ParcelFileDescriptor?,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback?
    ) {
        var input: InputStream?=null
        var output: OutputStream?=null
        try {


            val file:File=File(path)
            input=FileInputStream(file)
            output=FileOutputStream(destination?.fileDescriptor)
            var byte = byteArrayOf()
            var size:Int?=input.read(byte)
            if (size != null) {
                while (size>=0 &&!cancellationSignal?.isCanceled!!){
 output.write(byte,0,size)
                }
            }

            if(cancellationSignal?.isCanceled == true){
callback?.onWriteCancelled()
            }else{
callback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
            }




        }catch (e:Exception){
            e.printStackTrace()
        }


    }
}