package com.gcptrack.ui.activities

import android.os.Bundle
import com.gcptrack.base.FullScreenBaseActivity
import com.gcptrack.custom.gotoActivity
import com.gcptrack.utils.UserStateManager
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
                gotoActivity(DashboardActivity::class.java, clearAllActivity = true)
            } /*else if (UserStateManager.isOnBoardingComplete()) {
                gotoActivity(LoginContainerActivity::class.java, clearAllActivity = true)
            } */else {
//                gotoActivity(OnBoardingActivity::class.java, clearAllActivity = true)
                gotoActivity(LoginContainerActivity::class.java, clearAllActivity = true)
            }

        }
    }
}