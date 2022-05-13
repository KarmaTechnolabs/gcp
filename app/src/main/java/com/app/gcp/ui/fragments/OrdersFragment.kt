package com.app.gcp.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.app.gcp.R
import com.app.gcp.adapter.OrdersAdapter
import com.app.gcp.api.requestmodel.OrderListRequestModel
import com.app.gcp.api.responsemodel.OrderStatusResponse
import com.app.gcp.api.responsemodel.OrdersResponse
import com.app.gcp.base.BaseFragment
import com.app.gcp.custom.gotoActivity
import com.app.gcp.databinding.FragmentOrdersBinding
import com.app.gcp.listeners.ItemClickListener
import com.app.gcp.ui.activities.OrderDetailActivity
import com.app.gcp.ui.dialogs.OrderStatusFilterBottomSheet
import com.app.gcp.utils.Constants
import com.app.gcp.utils.UserStateManager
import com.app.gcp.viewmodel.DashBoardViewModel
import java.util.*

class OrdersFragment : BaseFragment(), View.OnClickListener,
    ItemClickListener<OrdersResponse>, OrderStatusFilterBottomSheet.OrderStatusListener {

    private var _binding: FragmentOrdersBinding? = null
    private val orderListArray = mutableListOf<OrdersResponse>()
    private var orderListAdapter: OrdersAdapter? = null
    private val dashboardViewModel by activityViewModels<DashBoardViewModel>()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var isFirstTime = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentOrdersBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.clickListener = this

        binding.swipeRefresh.setOnRefreshListener {
            callOrderListApi()
            binding.swipeRefresh.isRefreshing = true
        }

        dashboardViewModel.orderListResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(
                    response, isShowProgress = false,
                    successListener = object : (List<OrdersResponse>, String) -> Unit {
                        override fun invoke(it: List<OrdersResponse>, message: String) {
//                            showToast(message)
                            orderListArray.clear()
                            orderListArray.addAll(it)
                            orderListAdapter?.setItems(orderListArray as ArrayList<OrdersResponse?>)
                            checkNoData()
                            binding.swipeRefresh.isRefreshing = false
                        }
                    },
                    failureListener = object : () -> Unit {
                        override fun invoke() {
                            checkNoData()
                            binding.swipeRefresh.isRefreshing = false
                        }
                    })
            }
        }

        orderListAdapter = OrdersAdapter(activity)
        orderListAdapter?.setClickListener(this)
        binding.rvSearchOrder.adapter = orderListAdapter

        callOrderListApi()

        binding.svSearchOrder.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                orderListAdapter?.filter?.filter(query)
                Handler(Looper.getMainLooper()).postDelayed({
                    checkNoData()
                }, 100)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                orderListAdapter?.filter?.filter(newText)
                Handler(Looper.getMainLooper()).postDelayed({
                    checkNoData()
                }, 100)
                return false
            }
        })

//        binding.spnOrderStatus.onItemSelectedListener = object :
//            AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?, view: View?,
//                position: Int, id: Long
//            ) {
//                if (isFirstTime) {
//                    isFirstTime = false
//                }else {
//                val model = parent?.getItemAtPosition(position) as OrderStatusResponse
////                binding.edtOrderStatus.setText(model.title)
//                    dashboardViewModel.selectedOrderStatusFilter= model.id.toString()
//                callOrderListApi()
//                }
//
//            }
//
//            override fun onNothingSelected(arg0: AdapterView<*>?) {
//            }
//        }

    }

    private fun callOrderListApi() {
        dashboardViewModel.callOrderListAPI(
            OrderListRequestModel(
                search = "", status_id = dashboardViewModel.selectedOrderStatusFilter,"1",
                UserStateManager.getBearerToken()
            )
        )
    }

    private fun checkNoData() {
        if (orderListAdapter?.itemCount!! > 0) {
            binding.tvNoData.visibility = View.GONE
        } else {
            binding.tvNoData.visibility = View.VISIBLE
        }
    }

    private fun setOrderStatus() {
//        val statusAdapter =
//            OrderStatusAdapter(
//                activity,
//                R.layout.list_item_order_status_spinner,
//                R.id.tv_order_status,
//                dashboardViewModel.orderStatusArray
//            )
//        binding.spnOrderStatus.adapter = statusAdapter
//
//        binding.spnOrderStatus.performClick()

        val orderStatusBottomSheet =
            dashboardViewModel.orderStatusArray.let {
                OrderStatusFilterBottomSheet.newInstance(
                    it as ArrayList<OrderStatusResponse>,dashboardViewModel.selectedOrderStatusFilter
                )
            }
        orderStatusBottomSheet.setOnOrderStatusListener(this)
        orderStatusBottomSheet.show(childFragmentManager, "filter-picker")
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.ivFilterStatus -> setOrderStatus()
        }
    }

    override fun onItemClick(viewIdRes: Int, model: OrdersResponse, position: Int) {
        when (viewIdRes) {
            R.id.mcv_main -> {
                requireActivity().gotoActivity(
                    OrderDetailActivity::class.java,
                    bundle = bundleOf(Constants.EXTRA_ORDER_STATUS to model),
                    needToFinish = false
                )
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOrderStatusSelected(orderStatus: OrderStatusResponse) {
        dashboardViewModel.selectedOrderStatusFilter= orderStatus.id.toString()
        callOrderListApi()
    }
}