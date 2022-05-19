package com.app.gcp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.app.gcp.R
import com.app.gcp.adapter.OrderDetailAdditionalDetailsAdapter
import com.app.gcp.adapter.OrderDetailProductAdapter
import com.app.gcp.adapter.OrderDetailStageAdapter
import com.app.gcp.api.requestmodel.OrderDetailsRequestModel
import com.app.gcp.api.responsemodel.OrdersDetailsResponse
import com.app.gcp.api.responsemodel.OrdersResponse
import com.app.gcp.base.BaseActivity
import com.app.gcp.databinding.ActivityCustomerDetailBinding
import com.app.gcp.utils.Constants
import com.app.gcp.utils.UserStateManager
import com.app.gcp.viewmodel.OrderDetailViewModel

class CustomerDetailActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCustomerDetailBinding
    val viewModel: OrderDetailViewModel by viewModels()

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

        if (intent.extras != null && intent.hasExtra(Constants.EXTRA_ORDER_STATUS)) {
            viewModel.orderStatusResponse.value =
                intent?.getParcelableExtra<OrdersResponse>(Constants.EXTRA_ORDER_STATUS)
            callOrderDetailApi()
        }

        viewModel.orderDetailsResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(
                    response, isShowProgress = true,
                    successListener = object : (OrdersDetailsResponse, String) -> Unit {
                        override fun invoke(it: OrdersDetailsResponse, message: String) {
//                            showToast(message)
                            viewModel.orderDetailResponseLiveData.value = it

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
        viewModel.callOrderDetailAPI(
            OrderDetailsRequestModel(
                user_type = UserStateManager.getUserProfile()?.user_type.toString(),
                orderId = viewModel.orderStatusResponse.value?.orderId,
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