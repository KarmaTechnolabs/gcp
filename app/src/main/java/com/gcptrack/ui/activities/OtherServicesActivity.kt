package com.gcptrack.ui.activities

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.gcptrack.R
import com.gcptrack.base.BaseActivity
import com.gcptrack.databinding.ActivityOtherServicesBinding

class OtherServicesActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityOtherServicesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_other_services)
        binding.lifecycleOwner = this
        binding.clickListener = this
        binding.toolbarOrderStatus.ivBack.setOnClickListener(this)
        binding.toolbarOrderStatus.tvTitle.text = getString(R.string.services_title1)

        binding.webview.loadUrl("file:///android_asset/other_services.html")
        binding.webview.settings.javaScriptEnabled = true
    }

    override fun onClick(view: View?) {
        when (view) {

//            binding.labelServiceIcon, binding.labelServiceTitle -> {
//                if (binding.labelServiceIcon.isChecked) {
//                    binding.labelServiceDescription.visibility = View.VISIBLE
//                } else {
//                    binding.labelServiceDescription.visibility = View.GONE
//                }
//            }
            binding.toolbarOrderStatus.ivBack -> onBackPressed()
        }
    }
}