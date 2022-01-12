package com.app.gcp.ui.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.gcp.R
import com.app.gcp.base.FullScreenBaseActivity
import com.app.gcp.databinding.ActivityLoginContainerBinding

class LoginContainerActivity : FullScreenBaseActivity() {
    private lateinit var binding: ActivityLoginContainerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_container)
    }
}