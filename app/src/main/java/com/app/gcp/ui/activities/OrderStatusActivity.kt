package com.app.gcp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.app.gcp.R
import com.app.gcp.adapter.TrackOrderStatusAdapter
import com.app.gcp.api.responsemodel.TrackOrderResponse
import com.app.gcp.base.BaseActivity
import com.app.gcp.databinding.ActivityOrderStatusBinding
import com.app.gcp.utils.Constants


class OrderStatusActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOrderStatusBinding
    private val trackOrderResponse = MutableLiveData<TrackOrderResponse>()
    private val orderStatusListArray = mutableListOf<TrackOrderResponse.History>()
    private var orderStatusListAdapter: TrackOrderStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_status)

        orderStatusListAdapter = TrackOrderStatusAdapter(this)
        binding.rvOrderStatus.adapter = orderStatusListAdapter

        if (intent.extras != null && intent.hasExtra(Constants.EXTRA_TRACK_ORDER)) {
            trackOrderResponse.value = intent.getParcelableExtra(Constants.EXTRA_TRACK_ORDER)
            binding.tvOrder.text = trackOrderResponse.value?.orderTrackingNumber
            orderStatusListArray.clear()
            trackOrderResponse.value?.history?.let { orderStatusListArray.addAll(it) }
            orderStatusListAdapter?.setItems(orderStatusListArray as ArrayList<TrackOrderResponse.History?>)
        }
        initView()
    }

    private fun initView() {
        binding.lifecycleOwner = this
        binding.clickListener = this
        binding.model = trackOrderResponse.value
        binding.toolbarOrderStatus.tvTitle.text = getString(R.string.order_status)
//        binding.toolbarOrderStatus.isBackButtonHide=false
        binding.toolbarOrderStatus.ivBack.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view) {
            binding.toolbarOrderStatus.ivBack -> onBackPressed()
        }
    }
}