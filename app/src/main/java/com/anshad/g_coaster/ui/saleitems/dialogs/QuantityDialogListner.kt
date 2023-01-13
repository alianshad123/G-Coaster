package com.anshad.g_coaster.ui.saleitems.dialogs

import com.anshad.g_coaster.constants.enums.QuantityEvents

interface QuantityDialogListner {

    fun onDialogEvents(events: QuantityEvents,item:Any)

}