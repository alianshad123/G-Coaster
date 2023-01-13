package com.anshad.g_coaster.utils

import com.anshad.g_coaster.constants.enums.QuantityEvents

interface DialogEventListner {

    fun onDialogEvents(events: QuantityEvents, item:Any)

}