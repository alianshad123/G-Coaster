package com.anshad.g_coaster


import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.anshad.basestructure.ktx.AppCompatActivityKtx.viewBinding
import com.anshad.basestructure.model.LoadingMessageData
import com.anshad.basestructure.model.MessageData
import com.anshad.basestructure.utils.EventObserver
import com.anshad.g_coaster.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseAppActvity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    lateinit var mainViewModel: MainViewModel

    var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setSupportActionBar(binding.appbar.toolbar)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.mainViewModel = mainViewModel

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navigationView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        val navController: NavController = navHostFragment.navController


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_saleitems

            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
      /*  actionBarDrawerToggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, R.string.nav_open, R.string.nav_close)

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()

        // to make the Navigation drawer icon always appear on the action bar

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)*/
        setupViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle?.onOptionsItemSelected(item) == true) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun setupViewModel() {
        mainViewModel.action.observe(this, EventObserver {
            onPerformAction(it)
        })
        mainViewModel.infoMessage.observe(this, EventObserver {
            onInfoMessage(it)
        })
        mainViewModel.loading.observe(this, EventObserver {
            onLoadingMessage(it)
        })


    }

    override fun onInfoMessage(messageData: MessageData) {
        if (messageData.context == null) {
            messageData.context = this
        }
        showDialog(
            messageData.getTitle(),
            messageData.getMessage(),
            messageData.getPositiveButton(),
            messageData.positiveAction,
            messageData.getNegativeButton(),
            messageData.negativeAction,
            messageData.getNeutralButton(),
            messageData.triggerActionOnDismiss,
            messageData.canDismiss
        )
    }

    override fun onLoadingMessage(messageData: LoadingMessageData) {
        if (messageData.context == null) {
            messageData.context = this
        }
        binding.isLoading = messageData.isLoading
        binding.message = messageData.getMessage()
    }

    override fun onLogout() {
        //nothing to do now
    }

    private fun showDialog(
        title: String?,
        message: String?,
        positiveButton: String?,
        positiveAction: (() -> Unit)?,
        negativeButton: String?,
        negativeAction: (() -> Unit)?,
        neutralButton: String?,
        triggerActionOnDismiss: Boolean,
        canDismiss: Boolean
    ): AlertDialog {
        var isClicked = false
        val dialogBuilder = MaterialAlertDialogBuilder(this)
        dialogBuilder.setTitle(title)
        dialogBuilder.setMessage(message)
        positiveButton?.let {
            dialogBuilder.setPositiveButton(it) { dialog, _ ->
                isClicked = true
                positiveAction?.invoke()
                dialog?.dismiss()
            }
        }
        negativeButton?.let {
            dialogBuilder.setNegativeButton(it) { dialog, _ ->
                isClicked = true
                negativeAction?.invoke()
                dialog?.dismiss()
            }
        }

        neutralButton?.let {
            dialogBuilder.setNeutralButton(it) { dialog, _ ->
                isClicked = true
                dialog?.dismiss()
            }
        }
        if (negativeButton == null && positiveButton == null && neutralButton == null) {
            dialogBuilder.setNeutralButton(
                R.string.ok
            ) { dialog, _ ->
                isClicked = true
                dialog.dismiss()
            }
        }
        if (triggerActionOnDismiss) {
            dialogBuilder.setOnDismissListener {
                if (negativeAction != null && !isClicked) {
                    negativeAction.invoke()
                } else if (positiveAction != null && !isClicked) {
                    positiveAction.invoke()
                }
            }
        }
        dialogBuilder.setCancelable(canDismiss)
        val dialog = dialogBuilder.create()
        runOnUiThread { dialog.show() }
        return dialog
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_container)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}