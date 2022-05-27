package com.app.gcp.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.app.gcp.R
import com.app.gcp.adapter.OrdersFilterAdapter
import com.app.gcp.api.responsemodel.OrderStatusResponse
import com.app.gcp.base.BaseBottomSheetFragment
import com.app.gcp.databinding.OrderStatusFilterBottomSheetBinding
import com.app.gcp.listeners.ItemClickListener
import com.app.gcp.utils.Constants

class OrderStatusFilterBottomSheet : BaseBottomSheetFragment(), View.OnClickListener,
    ItemClickListener<OrderStatusResponse.OrderStatus> {

    private lateinit var binding: OrderStatusFilterBottomSheetBinding
    private var listener: OrderStatusListener? = null
    private val orderFilterListArray = mutableListOf<OrderStatusResponse.OrderStatus>()
    private var typeFilter: Int? = 0
    private var orderFilterAdapter: OrdersFilterAdapter? = null

    companion object {
        fun newInstance(
            orderStatusList: ArrayList<OrderStatusResponse.OrderStatus>,
            selectedOrderStatusFilter: String,
            selectedOrderStagesFilter: String,
            typeFilter: Int
        ): OrderStatusFilterBottomSheet {
            val alertBottomSheet = OrderStatusFilterBottomSheet()

            val args = Bundle()
            args.putSerializable(Constants.EXTRA_DATA, orderStatusList)
            args.putString(Constants.EXTRA_SELECTED_STATUS, selectedOrderStatusFilter)
            args.putString(Constants.EXTRA_SELECTED_STAGES, selectedOrderStagesFilter)
            args.putInt(Constants.EXTRA_FILTER_TYPE, typeFilter)

            alertBottomSheet.arguments = args
            return alertBottomSheet
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.order_status_filter_bottom_sheet,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivClose.setOnClickListener(this)
        orderFilterAdapter = OrdersFilterAdapter(activity)
        orderFilterAdapter?.setClickListener(this)
        binding.rvOrderStatus.adapter = orderFilterAdapter
        orderFilterListArray.clear()
        if (arguments != null && requireArguments().containsKey(Constants.EXTRA_DATA)) {

            arguments?.getParcelableArrayList<OrderStatusResponse.OrderStatus>(Constants.EXTRA_DATA)
                ?.let { orderFilterListArray.addAll(it) }
            orderFilterAdapter?.setItems(orderFilterListArray as ArrayList<OrderStatusResponse.OrderStatus?>)
            typeFilter = arguments?.getInt(Constants.EXTRA_FILTER_TYPE)
            if (typeFilter == 1) {
                orderFilterAdapter?.setSelectedFilter(arguments?.getString(Constants.EXTRA_SELECTED_STATUS))
                binding.tvAlertTitle.text=resources.getString(R.string.filter_by_status)
            } else if (typeFilter == 2) {
                orderFilterAdapter?.setSelectedFilter(arguments?.getString(Constants.EXTRA_SELECTED_STAGES))
                binding.tvAlertTitle.text=resources.getString(R.string.filter_by_stages)
            }
        }
//        prizeTypeFilter =
//            arguments?.getSerializable(Constants.EXTRA_DATA) as Int


    }

    interface OrderStatusListener {
        fun onOrderStatusSelected(
            orderStatus: OrderStatusResponse.OrderStatus
        )

        fun onOrderStagesSelected(
            orderStatus: OrderStatusResponse.OrderStatus
        )
    }

    fun setOnOrderStatusListener(orderStatus: OrderStatusListener) {
        this.listener = orderStatus
    }

    override fun onClick(view: View) {
        when (view) {
            binding.ivClose -> {
                dismiss()
            }
        }
    }

    override fun onItemClick(viewIdRes: Int, model: OrderStatusResponse.OrderStatus, position: Int) {
        if (typeFilter == 1) {
            listener?.onOrderStatusSelected(model)
        } else if (typeFilter == 2) {
            listener?.onOrderStagesSelected(model)
        }
        dismiss()
    }
}
