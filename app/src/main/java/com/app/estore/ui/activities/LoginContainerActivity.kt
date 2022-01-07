package com.app.estore.ui.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.estore.R
import com.app.estore.base.FullScreenBaseActivity
import com.app.estore.databinding.ActivityLoginContainerBinding

class LoginContainerActivity : FullScreenBaseActivity() {
    private lateinit var binding: ActivityLoginContainerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_container)
    }
}