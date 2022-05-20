package com.app.gcp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.app.gcp.R
import com.app.gcp.api.requestmodel.CustomerDetailsRequestModel
import com.app.gcp.api.responsemodel.CustomerDetailsResponse
import com.app.gcp.api.responsemodel.CustomersResponse
import com.app.gcp.base.BaseActivity
import com.app.gcp.databinding.ActivityCustomerDetailBinding
import com.app.gcp.utils.Constants
import com.app.gcp.utils.UserStateManager
import com.app.gcp.viewmodel.CustomerDetailViewModel

class CustomerDetailActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCustomerDetailBinding
    val viewModel: CustomerDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_detail)
        initview()

    }

    private fun initview() {
        binding.lifecycleOwner = this
        binding.clickListener = this
        binding.model = viewModel
        binding.toolbarOrderStatus.ivBack.setOnClickListener(this)
        binding.toolbarOrderStatus.ivMedia.setOnClickListener(this)
        binding.toolbarOrderStatus.tvTitle.text = getString(R.string.customer_detail)

        if (intent.extras != null && intent.hasExtra(Constants.EXTRA_CUSTOMER)) {
            viewModel.customerResponse.value =
                intent?.getParcelableExtra<CustomersResponse>(Constants.EXTRA_CUSTOMER)
            callOrderDetailApi()
        }

        viewModel.customerDetailsResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(
                    response, isShowProgress = true,
                    successListener = object : (CustomerDetailsResponse, String) -> Unit {
                        override fun invoke(it: CustomerDetailsResponse, message: String) {
//                            showToast(message)
                            viewModel.customerDetailResponseLiveData.value = it.client

                            binding.tvNoData.visibility = View.GONE
                        }
                    },
                    failureListener = object : () -> Unit {
                        override fun invoke() {
                            binding.tvNoData.visibility = View.VISIBLE
                        }
                    })
            }
        }
    }


    private fun callOrderDetailApi() {
        viewModel.callCustomerDetailAPI(
            CustomerDetailsRequestModel(
                user_type = UserStateManager.getUserProfile()?.user_type.toString(),
                clientId = viewModel.customerResponse.value?.id,
                token =
                UserStateManager.getBearerToken()
            )
        )
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.toolbarOrderStatus.ivBack -> onBackPressed()

        }
    }
}