package com.app.gcp.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.app.gcp.R
import com.app.gcp.adapter.OrdersAdapter
import com.app.gcp.adapter.OrdersStatusAdapter
import com.app.gcp.api.responsemodel.OrderStatusResponse
import com.app.gcp.base.BaseBottomSheetFragment
import com.app.gcp.databinding.OrderStatusFilterBottomSheetBinding
import com.app.gcp.listeners.ItemClickListener
import com.app.gcp.utils.Constants
import java.util.ArrayList

class OrderStatusFilterBottomSheet : BaseBottomSheetFragment(), View.OnClickListener,
    ItemClickListener<OrderStatusResponse> {

    private lateinit var binding: OrderStatusFilterBottomSheetBinding
    private var listener: OrderStatusListener? = null
    private val orderStatusListArray = mutableListOf<OrderStatusResponse>()
    private var orderStatusListAdapter: OrdersStatusAdapter? = null

    companion object {
        fun newInstance(
            orderStatusList: ArrayList<OrderStatusResponse>,
            selectedOrderStatusFilter: String
        ): OrderStatusFilterBottomSheet {
            val alertBottomSheet = OrderStatusFilterBottomSheet()

            val args = Bundle()
            args.putSerializable(Constants.EXTRA_DATA, orderStatusList)
            args.putString(Constants.EXTRA_SELECTED, selectedOrderStatusFilter)
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
        orderStatusListAdapter = OrdersStatusAdapter(activity)
        orderStatusListAdapter?.setClickListener(this)
        binding.rvOrderStatus.adapter = orderStatusListAdapter
        orderStatusListArray.clear()
        if(arguments!= null && requireArguments().containsKey(Constants.EXTRA_DATA)){

            arguments?.getParcelableArrayList<OrderStatusResponse>(Constants.EXTRA_DATA)
                ?.let { orderStatusListArray.addAll(it) }
            orderStatusListAdapter?.setItems(orderStatusListArray as ArrayList<OrderStatusResponse?>)
            orderStatusListAdapter?.setSelectedStatus(arguments?.getString(Constants.EXTRA_SELECTED))
        }
//        prizeTypeFilter =
//            arguments?.getSerializable(Constants.EXTRA_DATA) as Int





    }

    interface OrderStatusListener {
        fun onOrderStatusSelected(
            orderStatus: OrderStatusResponse
        )
    }

    fun setOnOrderStatusListener(orderStatus: OrderStatusListener) {
        this.listener = orderStatus
    }

    override fun onClick(v: View) {
        when (v) {
            binding.ivClose -> {
                dismiss()
            }
        }
    }

    override fun onItemClick(viewIdRes: Int, model: OrderStatusResponse, position: Int) {
        listener?.onOrderStatusSelected(model)
        dismiss()
    }
}
