package com.anshad.g_coaster.ui.saleitems.dialogs

import com.anshad.g_coaster.constants.enums.QuantityEvents
import com.anshad.g_coaster.model.ItemsModel

interface QuantityDialogListner {

    fun onDialogEvents(events: QuantityEvents,item:ItemsModel)

}