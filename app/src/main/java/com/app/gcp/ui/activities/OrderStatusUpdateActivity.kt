package com.app.gcp.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.app.gcp.R
import com.app.gcp.adapter.OrderStatusAdapter
import com.app.gcp.api.requestmodel.OrderStatusUpdateRequestModel
import com.app.gcp.api.requestmodel.TrackingOrderRequestModel
import com.app.gcp.api.responsemodel.OrderStatusResponse
import com.app.gcp.base.BaseActivity
import com.app.gcp.custom.showToast
import com.app.gcp.databinding.ActivityOrderStatusUpdateBinding
import com.app.gcp.utils.Constants
import com.app.gcp.utils.UserStateManager
import com.app.gcp.viewmodel.OrderStatusUpdateViewModel


class OrderStatusUpdateActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOrderStatusUpdateBinding
    val viewModel: OrderStatusUpdateViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_status_update)
        initview()
    }

    private fun initview() {
        binding.lifecycleOwner = this
        binding.clickListener = this
        binding.model = viewModel
        binding.toolbarOrderStatus.ivBack.setOnClickListener(this)
        binding.toolbarOrderStatus.tvTitle.text = getString(R.string.order_status_update)

        if (intent.extras != null && intent.hasExtra(Constants.EXTRA_ORDER_STATUS)) {
            viewModel.orderStatusResponse.postValue(intent.getParcelableExtra(Constants.EXTRA_ORDER_STATUS))
        }

//        binding.edtOrderStatus.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
//            if (hasFocus) {
//                binding.spnOrderStatus.performClick()
//            }
//        }
    }

    private fun setOrderStatus() {
        val statusAdapter =
            OrderStatusAdapter(
                this,
                R.layout.list_item_order_status_spinner,
                R.id.tv_order_status,
                viewModel.orderStatusArray
            )
        binding.spnOrderStatus.adapter = statusAdapter
        binding.spnOrderStatus.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                val model = parent?.getItemAtPosition(position) as OrderStatusResponse
//                binding.edtOrderStatus.setText(model.title)
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
            }
        }
        for ((index, item) in viewModel.orderStatusArray.withIndex()) {
            if (viewModel.orderStatusResponse.value?.orderStatus.equals(
                    item.title,
                    ignoreCase = true
                )
            ) {
                binding.spnOrderStatus.setSelection(index)
            }
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.toolbarOrderStatus.ivBack -> onBackPressed()
            binding.btnCancel -> {
                onBackPressed()
            }
            binding.btnSubmit -> {
//                viewModel.callOrderStatusUpdateAPI(
//                    OrderStatusUpdateRequestModel(
//                        orderId = viewModel.orderStatusResponse.value?.trackingNumber,
//                        statusId = viewModel.orderStatusArray[binding.spnOrderStatus.selectedItemPosition].id,
//                        note = binding.edtOrderComment.text.toString(),
//                        token = UserStateManager.getBearerToken()
//                    )
//                ).observe(this) { event ->
//                    event.getContentIfNotHandled()?.let { response ->
//                        manageAPIResource(response) { it, _ ->
//                            response.message?.let { it1 -> showToast(it1) }
//
//                            onBackPressed()
//                        }
//                    }
//                }
            }
        }
    }
}