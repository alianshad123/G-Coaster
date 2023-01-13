package com.anshad.g_coaster

import com.anshad.basestructure.model.Action
import com.anshad.basestructure.ui.BaseActivity

abstract class BaseAppActvity : BaseActivity() {

    override fun onPerformAction(action: Action) {
        when (action.task.action) {
            else -> {
                super.onPerformAction(action)
            }
        }
    }
}