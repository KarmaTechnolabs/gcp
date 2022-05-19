package com.app.gcp.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatTextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.app.gcp.R
import com.app.gcp.api.requestmodel.TrackingOrderRequestModel
import com.app.gcp.api.responsemodel.OrderStatusResponse
import com.app.gcp.base.BaseActivity
import com.app.gcp.custom.hideKeyboard
import com.app.gcp.custom.showToast
import com.app.gcp.databinding.ActivityDashboardBinding
import com.app.gcp.ui.dialogs.LogOutAlertDialog
import com.app.gcp.ui.fragments.LoginFragmentDirections
import com.app.gcp.utils.UserStateManager
import com.app.gcp.viewmodel.DashBoardViewModel
import com.google.android.material.navigation.NavigationView


class DashboardActivity : BaseActivity(), View.OnClickListener,
    LogOutAlertDialog.LogoutClickListener, DrawerLayout.DrawerListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDashboardBinding
    private val viewModel by viewModels<DashBoardViewModel>()
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)

        initView()
        callOrderStatusApi()
        callOrderStagApi()

        viewModel.orderStatusListResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(
                    response, isShowProgress = false,
                    successListener = object : (List<OrderStatusResponse>, String) -> Unit {
                        override fun invoke(it: List<OrderStatusResponse>, message: String) {
//                            showToast(message)
                            viewModel.orderStatusArray.clear()
                            viewModel.orderStatusArray.add(
                                OrderStatusResponse(
                                    id = "",
                                    color = "#000000",
                                    title = "All",
                                    sort = "",
                                    deleted = "0"
                                )
                            )
                            viewModel.orderStatusArray.addAll(it)
                        }
                    },
                    failureListener = object : () -> Unit {
                        override fun invoke() {

                        }
                    })
            }
        }
    }

    private fun callOrderStatusApi() {
        viewModel.callOrderStatusAPI(
            TrackingOrderRequestModel(tracking_number = "")
        )
    }

    private fun callOrderStagApi() {
        viewModel.getOrderStagListAPI().observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(response) { it, _ ->
                    viewModel.orderStagesArray.clear()
                    viewModel.orderStagesArray.add(
                        OrderStatusResponse(
                            id = "",
                            color = "#000000",
                            title = "All",
                            sort = "",
                            deleted = "0"
                        )
                    )
                    viewModel.orderStagesArray.addAll(it)
                }
            }
        }

    }

    private fun initView() {

        binding.clickListener = this
        setContentView(binding.root)

        setSupportActionBar(binding.appBarDashboard.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
//        val navController = findNavController(R.id.nav_host_fragment_content_dashboard)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_customers,R.id.nav_orders, R.id.nav_order_tracking, R.id.nav_change_password, R.id.nav_faqs
            ), drawerLayout
        )
        setupActionBarWithNavController(
            findNavController(R.id.nav_host_fragment_content_dashboard),
            appBarConfiguration
        )
        navView.setupWithNavController(findNavController(R.id.nav_host_fragment_content_dashboard))

        binding.drawerLayout.addDrawerListener(this)

//        val headerView: View = navigationView.inflateHeaderView(R.layout.navigation_header)
//        navView.findViewById<View>(R.id.navigation_header_text)
        val headerView: View = navView.getHeaderView(0)
        headerView.findViewById<AppCompatTextView>(R.id.tv_short_name).text =
            UserStateManager.getUserProfile()?.firstName
        headerView.findViewById<AppCompatTextView>(R.id.tv_name).text =
            UserStateManager.getUserProfile()?.firstName
        headerView.findViewById<AppCompatTextView>(R.id.tv_short_email).text =
            UserStateManager.getUserProfile()?.email

//        if (intent.extras != null && intent.hasExtra(Constants.EXTRA_DATA)) {
        if (UserStateManager.getUserProfile()?.user_type.equals(
                "client",
                ignoreCase = true
            ) && UserStateManager.getUserProfile()?.isPasswordChange.equals(
                "0",
                ignoreCase = true
            )
        ) {
            val navHost =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_dashboard) as NavHostFragment?

            navController = navHost!!.navController
//            val navInflater = navController.navInflater
//            val graph = navInflater.inflate(R.navigation.dashboard_navigation)
//            graph.setStartDestination(R.id.nav_change_password)
//            navController.graph = graph
            navController.navigate(R.id.nav_change_password);

        }

//        }
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