package com.app.masterproject.ui.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.masterproject.R
import com.app.masterproject.base.FullScreenBaseActivity
import com.app.masterproject.databinding.ActivityLoginContainerBinding

class LoginContainerActivity : FullScreenBaseActivity() {
    private lateinit var binding: ActivityLoginContainerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_container)
    }
}