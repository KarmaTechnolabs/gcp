package com.gcptrack.ui.activities

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.gcptrack.R
import com.gcptrack.base.BaseActivity
import com.gcptrack.custom.gotoActivity
import com.gcptrack.databinding.ActivityMainBinding
import com.gcptrack.utils.Constants
import com.gcptrack.utils.UserStateManager

class MainActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var categoryId:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initView()
    }

    private fun initView(){
        binding.toolbar.isBackButtonHide = true
        binding.bottomNavigation.itemIconTintList = null

        if (intent.extras!=null)
            categoryId = intent.getIntExtra(Constants.EXTRA_CATEGORY_TYPE,0)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val navController = navHost!!.navController
        binding.bottomNavigation.setupWithNavController(navController)
        binding.toolbar.ivMedia.setImageResource(R.drawable.ic_toolbar_login)
        binding.toolbar.ivMedia.setOnClickListener(this)
    }


    fun changeTheToolbar(shouldShowMiddleImage: Boolean, title: String = "") {
        binding.toolbar.isFromBottomMenu = shouldShowMiddleImage
        if (!UserStateManager.isUserLoggedIn())
            binding.toolbar.ivMedia.visibility = if (shouldShowMiddleImage) View.VISIBLE else View.INVISIBLE

        binding.toolbar.tvTitle.text = title
    }

    fun changeTheTab(@IdRes id: Int) {
        binding.bottomNavigation.selectedItemId = id
    }

    override fun onClick(p0: View?) {
        gotoActivity(LoginContainerActivity::class.java,needToFinish = false)
    }
}