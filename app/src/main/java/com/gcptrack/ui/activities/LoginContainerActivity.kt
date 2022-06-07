package com.gcptrack.ui.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.gcptrack.R
import com.gcptrack.base.FullScreenBaseActivity
import com.gcptrack.databinding.ActivityLoginContainerBinding

class LoginContainerActivity : FullScreenBaseActivity() {
    private lateinit var binding: ActivityLoginContainerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_container)
    }
}