package com.anshad.g_coaster.ui.cart

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.os.Parcelable
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.anshad.basestructure.model.LoadingMessageData
import com.anshad.basestructure.ui.BaseFragment
import com.anshad.basestructure.utils.EventObserver
import com.anshad.g_coaster.R
import com.anshad.g_coaster.databinding.FragmentCartBinding
import com.anshad.g_coaster.db.Cart
import com.anshad.g_coaster.utils.Utils
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import com.itextpdf.text.pdf.draw.VerticalPositionMark
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.khairo.async.AsyncEscPosPrinter
import com.khairo.async.AsyncUsbEscPosPrint
import com.khairo.escposprinter.connection.DeviceConnection
import com.khairo.escposprinter.connection.usb.UsbConnection
import com.khairo.escposprinter.connection.usb.UsbPrintersConnections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class CartFragment : BaseFragment<CartViewModel>(R.layout.fragment_cart), ItemClickListner {
    private var COUNTV: Int=0
    private val PERMISSION_REQUEST_CODE: Int =11
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override val viewModel: CartViewModel by viewModels()
    private val adapter = CartItemsAdapter(listOf(), this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        showCartItems()


        Dexter.withActivity(requireActivity())
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {

                }

            })


        viewModel.itemUpdated.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(requireContext(), "Item Removed", Toast.LENGTH_SHORT).show()
            }

        })
        viewModel.salesAdded.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it) {

                    viewModel.sale_success = true
                    CoroutineScope(Dispatchers.Main).launch {

                        viewModel.clearCart().also {
                            viewModel.isClicked = false

                        }

                    }

                    printUsb()
                }
            }
        })



        binding.discoutPer.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


                val dis = s?.toString()

                if (!dis.isNullOrBlank()) {
                    if ((dis?.toDouble() ?: 0.0) > 0.0) {
                        viewModel.updateDiscountPer(dis)
                    }
                } else {
                    viewModel.updateDiscountPer("0.0")
                }
            }
        })


        binding.roundoffRs.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.v("Test", "")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.v("Test", "")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val dis = s?.toString()
                if (dis.isNullOrBlank()) {
                    viewModel.updateDiscountRs("0.0")

                } else {
                    if ((dis?.toDouble() ?: 0.0) > 0.0) {
                        viewModel.updateDiscountRs(dis)
                    }
                }
            }
        })

        binding.checkout.setOnClickListener {

            if (!viewModel.isClicked) {
                viewModel.sale_success=false
                viewModel.isClicked = true
                viewModel.updateSale()


            }

        }

        binding.back.setOnClickListener {
            viewModel.navigateUp()

        }



        viewModel.loading_.observe(viewLifecycleOwner, EventObserver {
            _onLoadingMessage(it)
        })
    }

    private fun _onLoadingMessage(messageData: LoadingMessageData) {
        if (messageData.context == null) {
            messageData.context = requireContext()
        }
        binding.isLoading = messageData.isLoading
        binding.message = messageData.getMessage()

    }

    private fun printUsb() {
        val usbConnection = UsbPrintersConnections.selectFirstConnected(requireContext())
        val usbManager = requireContext().getSystemService(AppCompatActivity.USB_SERVICE) as UsbManager
        if (usbConnection == null) {
            android.app.AlertDialog.Builder(requireContext())
                .setTitle("USB Connection")
                .setMessage("No USB printer found.")
                .show()
            return
        }

        val permissionIntent = PendingIntent.getBroadcast(requireContext(), 0, Intent(ACTION_USB_PERMISSION), 0)
        val filter = IntentFilter(ACTION_USB_PERMISSION)
        requireContext().registerReceiver(usbReceiver, filter)
        usbManager.requestPermission(usbConnection.device, permissionIntent)
    }


    private fun createPdf(path: String) {
        if (File(path).exists()) {
            File(path).delete()

            try {
                val document: Document = Document()
                PdfWriter.getInstance(document, FileOutputStream(path))
                document.open()

                document.setPageSize(PageSize.B7)
                document.addCreationDate()
                document.addAuthor("G-Coster")
                document.addCreator("")

                val colorAccent: BaseColor = BaseColor(0, 153, 204, 255)
                val fontSize = 10.0f
                val valueFontsize = 13.0f

                val baseFont = BaseFont.createFont(
                    "assets/poppins_medium.ttf",
                    "UTF-8",
                    BaseFont.EMBEDDED
                )
                val titleFont: Font = Font(baseFont, 18.0f, Font.NORMAL, BaseColor.BLACK)
                addNewItem(document, "Order Details", Element.ALIGN_CENTER, titleFont)

                val orderNofont: Font = Font(baseFont, fontSize, Font.NORMAL, colorAccent)
                addNewItem(document, "Order No", Element.ALIGN_LEFT, orderNofont)

                val orderNoValueFont: Font =
                    Font(baseFont, valueFontsize, Font.NORMAL, BaseColor.BLACK)
                addNewItem(document, "#717171", Element.ALIGN_CENTER, orderNoValueFont)

                addLineSeperator(document)

                addNewItem(document, "Order Date", Element.ALIGN_LEFT, orderNofont)
                addNewItem(document, "3/08/2019", Element.ALIGN_LEFT, orderNoValueFont)
                addLineSeperator(document)

                addNewItem(document, "Account Name", Element.ALIGN_LEFT, orderNofont)
                addNewItem(document, "G-Coster", Element.ALIGN_LEFT, orderNoValueFont)
                addLineSeperator(document)

                addLineSpace(document)
                addNewItem(document, "Product Detail", Element.ALIGN_CENTER, titleFont)
                addLineSeperator(document)


                addNewItemWithLeftAndRight(document, "Pizza 25", "0.0%", titleFont,orderNoValueFont)
                addNewItemWithLeftAndRight(document, "20.0*1000", "12000", titleFont,orderNoValueFont)

                addLineSeperator(document)

                addNewItemWithLeftAndRight(document, "Pizza 25", "0.0%", titleFont,orderNoValueFont)
                addNewItemWithLeftAndRight(document, "20.0*1000", "12000", titleFont,orderNoValueFont)
                addLineSeperator(document)

                addLineSpace(document)
                addLineSpace(document)

                addNewItemWithLeftAndRight(document, "Total", "24000", titleFont,orderNoValueFont)

                document.close()

                Toast.makeText(requireContext(),"Success",Toast.LENGTH_SHORT).show()

                printPdf()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun printPdf(){
        val printManager:PrintManager= requireActivity().getSystemService(Context.PRINT_SERVICE) as PrintManager

        try {
            val printDocumentAdapter:PrintDocumentAdapter=PdfDocumentAdapter(requireActivity(),Utils.getAppPath(requireActivity()))

            printManager.print("Document",printDocumentAdapter,PrintAttributes.Builder().build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addNewItemWithLeftAndRight(
        document: Document,
        textLeft: String,
        textRight: String,
        titleFont: Font,
        orderNoValueFont: Font
    ) {
        val chunkTextLeft: Chunk = Chunk(textLeft, titleFont)
        val chunkTextRight: Chunk = Chunk(textRight, titleFont)
        val p: Paragraph = Paragraph(chunkTextRight)
        p.add(VerticalPositionMark())
        p.add(chunkTextRight)
        document.add(p)

    }

    private fun addNewItem(document: Document, text: String, align: Int, titleFont: Font) {
        val chunk: Chunk = Chunk(text, titleFont)
        val para: Paragraph = Paragraph(chunk)
        para.alignment = align
        document.add(para)

    }

    private fun addLineSeperator(document: Document) {
        val lineSeparator = LineSeparator()
        lineSeparator.lineColor = BaseColor(0, 0, 0, 68)
        addLineSpace(document)
        document.add(Chunk(lineSeparator))
        addLineSpace(document)
    }

    private fun addLineSpace(document: Document) {
        document.add(Paragraph(""))

    }

    private fun showCartItems() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getAllCartItems().observe(viewLifecycleOwner) { cartItems ->

                if (cartItems.isNotEmpty()) {
                    binding.emptyTxt.visibility = View.GONE
                    binding.recyclerview.visibility = View.VISIBLE
                    binding.paymentData.visibility = View.VISIBLE
                    binding.checkout.visibility = View.VISIBLE

                    viewModel.updateFields(cartItems)

                    adapter.updateData(cartItems)
                } else {
                    if(viewModel.sale_success) {
                            binding.successContainer.visibility=View.VISIBLE
                            binding.emptyTxt.visibility = View.GONE
                            binding.recyclerview.visibility = View.GONE
                            binding.paymentData.visibility = View.GONE
                            binding.checkout.visibility = View.GONE

                    }else{
                        binding.emptyTxt.visibility = View.VISIBLE
                        binding.recyclerview.visibility = View.GONE
                        binding.paymentData.visibility = View.GONE
                        binding.checkout.visibility = View.GONE
                        binding.successContainer.visibility=View.GONE
                    }


                }

            }
        }
    }


    override fun onDataItemClicked(itemData: Cart) {

        showDeleteAlert(itemData)
    }

    fun showDeleteAlert(itemData: Cart) {

        val builder = AlertDialog.Builder(requireContext())
        //set title for alert dialog
        builder.setTitle(R.string.alert)
        //set message for alert dialog
        builder.setMessage(R.string.delete_confirmation)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            deleteItem(itemData)
        }

        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun deleteItem(itemData: Cart) {
        CoroutineScope(Dispatchers.Main).launch {

            viewModel.deleteCartItem(itemData).also {
                showCartItems()
                viewModel.getItemData(itemData.itemId, itemData.quantity)
            }

        }
    }
    private val ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION"
    private val usbReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (ACTION_USB_PERMISSION == action) {
                synchronized(this) {
                    val usbManager = requireContext().getSystemService(AppCompatActivity.USB_SERVICE) as UsbManager
                    val usbDevice =
                        intent.getParcelableExtra<Parcelable>(UsbManager.EXTRA_DEVICE) as UsbDevice?
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (usbDevice != null) {
                            Toast.makeText(requireContext(),"broadcast",Toast.LENGTH_SHORT).show()
                            val runner = AsyncUsbEscPosPrint(context)
                            runner.execute( getAsyncEscPosPrinter(
                                UsbConnection(
                                    usbManager,
                                    usbDevice
                                )
                            ))

                        /*    // printIt(new UsbConnection(usbManager, usbDevice));
                         AsyncUsbEscPosPrint(context)
                                .execute(
                                    getAsyncEscPosPrinter(
                                        UsbConnection(
                                            usbManager,
                                            usbDevice
                                        )
                                    )
                                )*/

                        }
                    }
                }
            }
        }
    }


    /**
     * Asynchronous printing
     */
    @SuppressLint("SimpleDateFormat")
    fun getAsyncEscPosPrinter(printerConnection: DeviceConnection?): AsyncEscPosPrinter {
        val format = SimpleDateFormat("dd-MM-yyyy")
        val printer = AsyncEscPosPrinter(printerConnection!!, 203, 60f, 32)
        // createPdf(Utils.getAppPath(requireActivity()))
        val textToPrint= "[C]\n" +
                "[C]           ======= G-COSTER =======\n"+
                "[C]              SHOE HUB\n"+
                "[C]           Let's explore the fashion\n"+
                "[C]               CourtRoad,\n"+
                "[C]             Alathur\n"+
                "[C]           \n" +
                "[L]Mob:8075617932\n" +
                "[C]-----------------------------------------------\n"+
                "[L]\n" +
                "[L]Sales#:${viewModel.saleItems?.get(0)?.saleId}[R]                  <font size='small'>"+ format.format(Date()) + "</font>\n"+
                "[C]-----------------------------------------------\n"+
                "[L]Item[C]       Quantity       [R]Amount\n" +
                "[C]-----------------------------------------------\n"+
                getItems()+"\n"+
                "[C]-----------------------------------------------\n"+
                "[C]           \n" +
                "[C]           \n" +
                "[L]Total :${viewModel.saleItems?.size}[R]                  ${viewModel.sale?.billamount} INR\n" +
                "[C]-----------------------------------------------\n"+
                "[L]Discount : ${viewModel.sale?.discount} INR\n" +
                "[L]RoundOff : ${viewModel.sale?.roundoff} INR\n" +
                "[C]-----------------------------------------------\n"+
                "[L]Net Total : ${viewModel.sale?.grosstotal} INR\n" +
                "[C]-----------------------------------------------\n"+
                "\n"+
                "[C]      **THANK YOU FOR SHOPPING WITH US**\n"


        return printer.setTextToPrint(textToPrint)


    }

    private fun getItems(): String? {
        var items:String=""
        viewModel.saleItems?.forEach {
            if(!it.name.isNullOrBlank()){
                items += "[L]${it.name}[C]       ${it.quantity}       [R]       ${it.sellingprize} INR\n"
            }else{
                items += "[L]${it.codename}[C]       ${it.quantity}       [R]       ${it.sellingprize} INR\n"
            }

        }

     return items
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            context?.unregisterReceiver(usbReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}



