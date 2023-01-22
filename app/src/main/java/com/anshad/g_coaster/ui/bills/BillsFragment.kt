package com.anshad.g_coaster.ui.bills

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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anshad.basestructure.model.LoadingMessageData
import com.anshad.basestructure.ui.BaseFragment
import com.anshad.basestructure.utils.EventObserver
import com.anshad.g_coaster.R
import com.anshad.g_coaster.databinding.FragmentBillsBinding
import com.anshad.g_coaster.model.BillList
import com.khairo.async.AsyncEscPosPrinter
import com.khairo.async.AsyncUsbEscPosPrint
import com.khairo.escposprinter.connection.DeviceConnection
import com.khairo.escposprinter.connection.usb.UsbConnection
import com.khairo.escposprinter.connection.usb.UsbPrintersConnections
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class BillsFragment :  BaseFragment<BillsViewModel>(R.layout.fragment_bills),
    ItemClickListner {

    private val PERMISSION_REQUEST_CODE: Int =11
    private val ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION"
    private var printText=""

    private var _binding: FragmentBillsBinding? = null
    private val binding get() = _binding!!

    override val viewModel: BillsViewModel by viewModels()
    private val adapter = BillsListAdapter(listOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.dateKey = arguments?.getString("dateKey") as String

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBillsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      viewModel?.date?.let {
          viewModel.getBills(it)
      }

        viewModel.billsData.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                if(it.result?.size!! >0){
                    adapter.updateData(it?.result!!)
                }else{

                }

            }else{

            }
        })
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

    override fun onDataItemClicked(billsData: BillList,view: View) {
        when(view.id) {
           R.id.btn_reprint-> rePrint(billsData)
            else -> viewModel.navigateToSoldItems(viewModel.date, billsData.id)
        }
    }

    private fun rePrint(billsData: BillList) {


        val builder = AlertDialog.Builder(requireContext())
        //set title for alert dialog
        builder.setTitle(R.string.alert)
        //set message for alert dialog
        builder.setMessage(R.string.are_you_sure)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            printUsb(billsData.print)
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

    private fun printUsb(print: String?) {

        val usbConnection = UsbPrintersConnections.selectFirstConnected(requireContext())
        val usbManager = requireContext().getSystemService(AppCompatActivity.USB_SERVICE) as UsbManager
        if (usbConnection == null) {

            android.app.AlertDialog.Builder(requireContext())
                .setTitle("USB Connection")
                .setMessage("No USB printer found.")
                .show()
            return
        }
        printText=print?:""

        val permissionIntent = PendingIntent.getBroadcast(requireContext(), 0, Intent(ACTION_USB_PERMISSION), 0)
        val filter = IntentFilter(ACTION_USB_PERMISSION)
        requireContext().registerReceiver(usbReceiver, filter)
        usbManager.requestPermission(usbConnection.device, permissionIntent)
    }


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

                            val runner = AsyncUsbEscPosPrint(context)
                            runner.execute( getAsyncEscPosPrinter(
                                UsbConnection(
                                    usbManager,
                                    usbDevice
                                ),printText
                            ))


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
    fun getAsyncEscPosPrinter(printerConnection: DeviceConnection?, printText: String): AsyncEscPosPrinter {
        val printer = AsyncEscPosPrinter(printerConnection!!, 203, 60f, 32)
        val textToPrint= printText
        return printer.setTextToPrint(textToPrint)

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