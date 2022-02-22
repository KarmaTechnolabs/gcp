package com.app.gcp.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.app.gcp.R
import com.app.gcp.base.BaseActivity
import com.app.gcp.custom.hideKeyboard
import com.app.gcp.custom.showToast
import com.app.gcp.databinding.ActivityDashboardBinding
import com.app.gcp.ui.dialogs.LogOutAlertDialog
import com.app.gcp.utils.UserStateManager
import com.app.gcp.utils.Utils
import com.app.gcp.viewmodel.DashBoardViewModel
import com.app.gcp.viewmodel.MoreViewModel
import android.widget.Toast

private var back_pressed: Long = 0
class DashboardActivity : BaseActivity(), View.OnClickListener,
    LogOutAlertDialog.LogoutClickListener, DrawerLayout.DrawerListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDashboardBinding
    private val dashBoardViewModel by viewModels<DashBoardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)

        initView()

    }

    private fun initView() {

        binding.clickListener = this
        setContentView(binding.root)

        setSupportActionBar(binding.appBarDashboard.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_dashboard)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_orders, R.id.nav_order_tracking, R.id.nav_faqs
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.drawerLayout.addDrawerListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dashboard, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_dashboard)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.tvLogout -> showLogOutDialog()
        }
    }

    private fun showLogOutDialog() {
        val logOutDialog =
            LogOutAlertDialog.newInstance(getString(R.string.are_you_sure_you_want_to_logout))
        logOutDialog.setListener(this)
        logOutDialog.show(supportFragmentManager, "Log-out")
    }

    override fun onLogOutClick() {
        showToast("Logout successfully.")
        UserStateManager.logout(this)
//        dashBoardViewModel.callLogoutAPI.observe(this) { event ->
//            event.getContentIfNotHandled()?.let { response ->
//                manageAPIResource(response) { _, message ->
//                    showToast(message)
//                    UserStateManager.logout(this)
//                }
//            }
//        }
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        hideKeyboard(drawerView)
        window.decorView.clearFocus()
    }

    override fun onDrawerOpened(drawerView: View) {
        hideKeyboard(drawerView)
        window.decorView.clearFocus()
    }

    override fun onDrawerClosed(drawerView: View) {
        hideKeyboard(drawerView)
        window.decorView.clearFocus()
    }

    override fun onDrawerStateChanged(newState: Int) {

    }
//    override fun onBackPressed() {
//        if (back_pressed + 2000 > System.currentTimeMillis())
//            super.onBackPressed()
//        else
//            showToast(resources.getString(R.string.press_again))
//        back_pressed = System.currentTimeMillis()
//    }

}