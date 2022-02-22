package com.app.gcp.ui.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.gcp.R
import com.app.gcp.base.BaseActivity
import com.app.gcp.databinding.ActivityOrderDetailBinding
import com.app.gcp.databinding.ActivityOrderStatusBinding

class OrderDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityOrderDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)
initview()
    }

    private fun initview() {
        binding.lifecycleOwner=this
    }
}