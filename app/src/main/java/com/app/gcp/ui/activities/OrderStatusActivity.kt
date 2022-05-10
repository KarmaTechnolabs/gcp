package com.app.gcp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.app.gcp.R
import com.app.gcp.api.requestmodel.LoginRequestModel
import com.app.gcp.api.responsemodel.TrackOrderResponse
import com.app.gcp.base.BaseActivity
import com.app.gcp.databinding.ActivityOrderStatusBinding
import com.app.gcp.utils.Constants


class OrderStatusActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOrderStatusBinding
    private val trackOrderResponse = MutableLiveData<TrackOrderResponse>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_status)

        initView()

        if (intent.extras!=null && intent.hasExtra(Constants.EXTRA_TRACK_ORDER)){
            trackOrderResponse.postValue(intent.getParcelableExtra(Constants.EXTRA_TRACK_ORDER))
            binding.tvOrder.text=trackOrderResponse.value?.orderTrackingNumber
            getOrderStatus("3")
        }


    }

    private fun initView() {
        binding.lifecycleOwner=this
        binding.clickListener=this
        binding.toolbarOrderStatus.tvTitle.text = getString(R.string.order_status)
//        binding.toolbarOrderStatus.isBackButtonHide=false
        binding.toolbarOrderStatus.ivBack.setOnClickListener(this)

    }

    private fun getOrderStatus(orderStatus: String) {
        when (orderStatus) {
            "0" -> {
                val alfa = 0.5.toFloat()
                setStatus(alfa)
            }
            "1" -> {
                val alfa = 1.toFloat()
                setStatus1(alfa)
            }
            "2" -> {
                val alfa = 1.toFloat()
                setStatus2(alfa)
            }
            "3" -> {
                val alfa = 1.toFloat()
                setStatus3(alfa)
            }
        }
    }


    private fun setStatus(alfa: Float) {
        val myf = 0.5.toFloat()
        binding.viewOrderPlaced.background=ContextCompat.getDrawable(this,R.drawable.shape_status_completed)
        binding.viewOrderConfirmed.background=ContextCompat.getDrawable(this,R.drawable.shape_status_current)
        binding.viewOrderProcessed.alpha=alfa
        binding.viewOrderProcessed.background=ContextCompat.getDrawable(this,R.drawable.shape_status_current)
        binding.conDivider.background=ContextCompat.getDrawable(this,R.drawable.shape_status_current)
        binding.placedDivider.alpha=alfa
        binding.imgOrderconfirmed.alpha=alfa
        binding.placedDivider.background=ContextCompat.getDrawable(this,R.drawable.shape_status_current)
        binding.textConfirmed.alpha=alfa
        binding.textorderprocessed.alpha=alfa
        binding.viewOrderPickup.background=ContextCompat.getDrawable(this,R.drawable.shape_status_current)
        binding.readyDivider.background=ContextCompat.getDrawable(this,R.drawable.shape_status_current)
        binding.orderpickup.alpha=alfa
        binding.textorderpickup.alpha=myf
    }

    private fun setStatus1(alfa: Float) {
        val myf = 0.5.toFloat()
        binding.viewOrderPlaced.background=ContextCompat.getDrawable(this,R.drawable.shape_status_completed)
        binding.viewOrderConfirmed.background=ContextCompat.getDrawable(this,R.drawable.shape_status_completed)
        binding.orderprocessed.alpha=myf
        binding.viewOrderProcessed.background=ContextCompat.getDrawable(this,R.drawable.shape_status_current)
        binding.conDivider.background=ContextCompat.getDrawable(this,R.drawable.shape_status_current)
        binding.placedDivider.background=ContextCompat.getDrawable(this,R.drawable.shape_status_completed)
        binding.imgOrderconfirmed.alpha=alfa
        binding.textConfirmed.alpha=alfa
        binding.textorderprocessed.alpha=myf
        binding.viewOrderPickup.alpha=myf
        binding.readyDivider.background=ContextCompat.getDrawable(this,R.drawable.shape_status_current)
        binding.orderpickup.alpha=myf
        binding.viewOrderPickup.background=ContextCompat.getDrawable(this,R.drawable.shape_status_current)
        binding.textorderpickup.alpha=myf
    }

    private fun setStatus2(alfa: Float) {
        val myf = 0.5.toFloat()
        binding.viewOrderPlaced.background=ContextCompat.getDrawable(this,R.drawable.shape_status_completed)
        binding.viewOrderConfirmed.background=ContextCompat.getDrawable(this,R.drawable.shape_status_completed)
        binding.orderprocessed.alpha=alfa
        binding.viewOrderProcessed.background=ContextCompat.getDrawable(this,R.drawable.shape_status_completed)
        binding.conDivider.background=ContextCompat.getDrawable(this,R.drawable.shape_status_completed)
        binding.placedDivider.background=ContextCompat.getDrawable(this,R.drawable.shape_status_completed)
        binding.imgOrderconfirmed.alpha=alfa
        binding.textConfirmed.alpha=alfa
        binding.textorderprocessed.alpha=alfa
        binding.viewOrderPickup.background=ContextCompat.getDrawable(this,R.drawable.shape_status_current)
        binding.readyDivider.background=ContextCompat.getDrawable(this,R.drawable.shape_status_current)
        binding.textorderpickup.alpha=myf
        binding.orderpickup.alpha=myf
    }

    private fun setStatus3(alfa: Float) {
        binding.viewOrderPlaced.background=ContextCompat.getDrawable(this,R.drawable.shape_status_completed)
        binding.viewOrderConfirmed.background=ContextCompat.getDrawable(this,R.drawable.shape_status_completed)
        binding.orderprocessed.alpha=alfa
        binding.viewOrderProcessed.background=ContextCompat.getDrawable(this,R.drawable.shape_status_completed)
        binding.conDivider.background=ContextCompat.getDrawable(this,R.drawable.shape_status_completed)
        binding.imgOrderconfirmed.alpha=alfa
        binding.placedDivider.background=ContextCompat.getDrawable(this,R.drawable.shape_status_completed)
        binding.textConfirmed.alpha=alfa
        binding.textorderprocessed.alpha=alfa
        binding.viewOrderPickup.background=ContextCompat.getDrawable(this,R.drawable.shape_status_completed)
        binding.readyDivider.background=ContextCompat.getDrawable(this,R.drawable.shape_status_completed)
        binding.textorderpickup.alpha=alfa
        binding.orderpickup.alpha=alfa
    }

    override fun onClick(v: View?) {
        onBackPressed()
    }
}