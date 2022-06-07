package com.gcptrack.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatTextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gcptrack.R
import com.gcptrack.api.requestmodel.OrderStatusRequestModel
import com.gcptrack.api.responsemodel.OrderStatusResponse
import com.gcptrack.base.BaseActivity
import com.gcptrack.custom.hideKeyboard
import com.gcptrack.custom.showToast
import com.gcptrack.databinding.ActivityDashboardBinding
import com.gcptrack.ui.dialogs.LogOutAlertDialog
import com.gcptrack.utils.UserStateManager
import com.gcptrack.viewmodel.DashBoardViewModel
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
                    successListener = object : (OrderStatusResponse, String) -> Unit {
                        override fun invoke(it: OrderStatusResponse, message: String) {
//                            showToast(message)
                            viewModel.orderStatusArray.clear()
                            viewModel.orderStatusArray.add(
                                OrderStatusResponse.OrderStatus(
                                    id = "",
                                    color = "#000000",
                                    title = "All",
                                    sort = "",
                                    deleted = "0"
                                )
                            )
                            it.orderStatus?.let { it1 -> viewModel.orderStatusArray.addAll(it1) }

                            if (it.users?.isPermissionUpdated.equals("1")) {
                                showToast("Your access writes are changed,need to login again.")
                                UserStateManager.logout(this@DashboardActivity)
                            }
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
            OrderStatusRequestModel(
                tracking_number = "",
                user_type = UserStateManager.getUserProfile()?.user_type.toString(),
                token = UserStateManager.getBearerToken()
            )
        )
    }

    private fun callOrderStagApi() {
        viewModel.getOrderStagListAPI().observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(response) { it, _ ->
                    viewModel.orderStagesArray.clear()
                    viewModel.orderStagesArray.add(
                        OrderStatusResponse.OrderStatus(
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
                R.id.nav_customers,
                R.id.nav_orders,
                R.id.nav_order_tracking,
                R.id.nav_change_password,
                R.id.nav_faqs
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

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_dashboard) as NavHostFragment?

        navController = navHost!!.navController
//        if (intent.extras != null && intent.hasExtra(Constants.EXTRA_DATA)) {
        if (UserStateManager.getUserProfile()?.user_type.equals(
                "client",
                ignoreCase = true
            )
        ) {
            navView.menu.findItem(R.id.nav_customers).isVisible = false
            if (UserStateManager.getUserProfile()?.isPasswordChange.equals(
                    "0",
                    ignoreCase = true
                )
            ) {

//            val navInflater = navController.navInflater
//            val graph = navInflater.inflate(R.navigation.dashboard_navigation)
//            graph.setStartDestination(R.id.nav_change_password)
//            navController.graph = graph
                navController.navigate(R.id.nav_change_password)
            }
        }
        if (UserStateManager.getUserProfile()?.user_type.equals(
                "admin",
                ignoreCase = true
            ) && !UserStateManager.getUserProfile()?.permissions?.order.equals(
                "all",
                ignoreCase = true
            )
        ) {
            navView.menu.findItem(R.id.nav_orders).isVisible = false
            val navInflater = navController.navInflater
            val graph = navInflater.inflate(R.navigation.dashboard_navigation)
            graph.setStartDestination(R.id.nav_customers)
            navController.graph = graph
        }
        if (UserStateManager.getUserProfile()?.user_type.equals(
                "admin",
                ignoreCase = true
            ) && !UserStateManager.getUserProfile()?.permissions?.client.equals(
                "1",
                ignoreCase = true
            )
        ) {
            navView.menu.findItem(R.id.nav_customers).isVisible = false
        }

        if (UserStateManager.getUserProfile()?.user_type.equals(
                "admin",
                ignoreCase = true
            ) && !UserStateManager.getUserProfile()?.permissions?.order.equals(
                "all",
                ignoreCase = true
            ) && !UserStateManager.getUserProfile()?.permissions?.client.equals(
                "1",
                ignoreCase = true
            )
        ) {
            val navInflater = navController.navInflater
            val graph = navInflater.inflate(R.navigation.dashboard_navigation)
            graph.setStartDestination(R.id.nav_order_tracking)
            navController.graph = graph
        }

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