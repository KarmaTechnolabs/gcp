package com.app.gcp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.app.gcp.R
import com.app.gcp.api.requestmodel.OrderDetailsRequestModel
import com.app.gcp.api.responsemodel.OrdersDetailsResponse
import com.app.gcp.api.responsemodel.OrdersResponse
import com.app.gcp.base.BaseActivity
import com.app.gcp.databinding.ActivityOrderDetailBinding
import com.app.gcp.utils.Constants
import com.app.gcp.utils.UserStateManager
import com.app.gcp.viewmodel.OrderDetailViewModel
import java.util.ArrayList

class OrderDetailActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOrderDetailBinding
    val viewModel: OrderDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)
        initview()
    }

    private fun initview() {
        binding.lifecycleOwner = this
        binding.clickListener = this
        binding.model = viewModel
        binding.toolbarOrderStatus.ivBack.setOnClickListener(this)
        binding.toolbarOrderStatus.tvTitle.text = getString(R.string.order_detail)

        if (intent.extras != null && intent.hasExtra(Constants.EXTRA_ORDER_STATUS)) {
            viewModel.orderStatusResponse.postValue(intent.getParcelableExtra(Constants.EXTRA_ORDER_STATUS))
            callOrderDetailApi()
        }

        viewModel.orderDetailsResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(
                    response, isShowProgress = false,
                    successListener = object : (OrdersDetailsResponse, String) -> Unit {
                        override fun invoke(it: OrdersDetailsResponse, message: String) {
//                            showToast(message)
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
                orderId = viewModel.orderStatusResponse.value?.id,
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