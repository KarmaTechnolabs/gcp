package com.app.estore.ui.activities

import android.os.Bundle
import com.app.estore.base.FullScreenBaseActivity
import com.app.estore.custom.gotoActivity
import com.app.estore.utils.UserStateManager
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : FullScreenBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Timer("splash", false).schedule(3000) {

            if (UserStateManager.isUserLoggedIn()) {
                gotoActivity(MainActivity::class.java, clearAllActivity = true)
            } else if (UserStateManager.isOnBoardingComplete()) {
                gotoActivity(LoginContainerActivity::class.java, clearAllActivity = true)
            } else {
                gotoActivity(OnBoardingActivity::class.java, clearAllActivity = true)
            }

        }
    }
}