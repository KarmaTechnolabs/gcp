package com.gcptrack.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.gcptrack.R
import com.gcptrack.adapter.OrdersAdapter
import com.gcptrack.api.requestmodel.CustomerDetailsRequestModel
import com.gcptrack.api.responsemodel.CustomerDetailsResponse
import com.gcptrack.api.responsemodel.OrdersResponse
import com.gcptrack.base.BaseActivity
import com.gcptrack.custom.gotoActivity
import com.gcptrack.databinding.ActivityCustomerDetailBinding
import com.gcptrack.listeners.ItemClickListener
import com.gcptrack.utils.Constants
import com.gcptrack.utils.UserStateManager
import com.gcptrack.viewmodel.CustomerDetailViewModel

class CustomerDetailActivity : BaseActivity(), View.OnClickListener,
    ItemClickListener<OrdersResponse> {

    private lateinit var binding: ActivityCustomerDetailBinding
    val viewModel: CustomerDetailViewModel by viewModels()
    private val orderListArray = mutableListOf<OrdersResponse>()
    private var orderListAdapter: OrdersAdapter? = null

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
            viewModel.customerId.value =
                intent?.getStringExtra(Constants.EXTRA_CUSTOMER)
            callOrderDetailApi()
        }

        orderListAdapter = OrdersAdapter(this)
        orderListAdapter?.setClickListener(this)
        binding.rvSearchOrder.adapter = orderListAdapter

        viewModel.customerDetailsResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(
                    response, isShowProgress = true,
                    successListener = object : (CustomerDetailsResponse, String) -> Unit {
                        override fun invoke(it: CustomerDetailsResponse, message: String) {
//                            showToast(message)
                            viewModel.customerDetailResponseLiveData.value = it.client
                            orderListArray.clear()
                            it.orders?.let { it1 -> orderListArray.addAll(it1) }
                            orderListAdapter?.setItems(orderListArray as ArrayList<OrdersResponse?>)
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
                clientId = viewModel.customerId.value,
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

    override fun onItemClick(viewIdRes: Int, model: OrdersResponse, position: Int) {
        when (viewIdRes) {
            R.id.mcv_main -> {
                if ((UserStateManager.getUserProfile()?.user_type.equals(
                        "admin",
                        ignoreCase = true
                    ) && UserStateManager.getUserProfile()?.permissions?.order.equals(
                        "all",
                        ignoreCase = true
                    ))
                ) {
                    gotoActivity(
                        OrderDetailActivity::class.java,
                        bundle = bundleOf(Constants.EXTRA_ORDER_STATUS to model),
                        needToFinish = false
                    )
                }
            }
//            R.id.tv_order_status -> {
//                requireActivity().gotoActivity(
//                    OrderStatusUpdateActivity::class.java,
//                    bundle = bundleOf(Constants.EXTRA_ORDER_STATUS to model),
//                    needToFinish = false
//                )
//            }
        }
    }
}