package com.app.gcp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.app.gcp.R
import com.app.gcp.base.BaseActivity
import com.app.gcp.databinding.ActivityOrderStatusUpdateBinding

class OrderStatusUpdateActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOrderStatusUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_status_update)
initview()
    }

    private fun initview() {
        binding.lifecycleOwner=this
        binding.clickListener = this
    }

    override fun onClick(view: View?) {

    }
}