package com.app.masterproject.ui.activities

import android.os.Bundle
import com.app.masterproject.base.FullScreenBaseActivity
import com.app.masterproject.custom.gotoActivity
import com.app.masterproject.utils.UserStateManager
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