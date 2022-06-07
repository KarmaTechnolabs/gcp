package com.gcptrack.ui.fragments

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
import com.gcptrack.R
import com.gcptrack.adapter.OrdersAdapter
import com.gcptrack.api.requestmodel.OrderListRequestModel
import com.gcptrack.api.responsemodel.OrderStatusResponse
import com.gcptrack.api.responsemodel.OrdersResponse
import com.gcptrack.base.BaseFragment
import com.gcptrack.custom.gotoActivity
import com.gcptrack.databinding.FragmentOrdersBinding
import com.gcptrack.listeners.ItemClickListener
import com.gcptrack.ui.activities.CustomerDetailActivity
import com.gcptrack.ui.activities.OrderDetailActivity
import com.gcptrack.ui.dialogs.OrderStatusFilterBottomSheet
import com.gcptrack.utils.Constants
import com.gcptrack.utils.UserStateManager
import com.gcptrack.viewmodel.DashBoardViewModel

class OrdersFragment : BaseFragment(), View.OnClickListener,
    ItemClickListener<OrdersResponse>, OrderStatusFilterBottomSheet.OrderStatusListener {

    private var _binding: FragmentOrdersBinding? = null
    private val orderListArray = mutableListOf<OrdersResponse>()
    private var orderListAdapter: OrdersAdapter? = null
    private val dashboardViewModel by activityViewModels<DashBoardViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
                    response, isShowProgress = true,
                    successListener = object : (List<OrdersResponse>, String) -> Unit {
                        override fun invoke(it: List<OrdersResponse>, message: String) {
//                            showToast(message)
                            orderListArray.clear()
                            orderListArray.addAll(it)
                            orderListAdapter?.setItems(orderListArray as ArrayList<OrdersResponse?>)
                            Handler(Looper.getMainLooper()).postDelayed({
                                checkNoData()
                            }, 100)
                            binding.swipeRefresh.isRefreshing = false
                        }
                    },
                    failureListener = object : () -> Unit {
                        override fun invoke() {
                            Handler(Looper.getMainLooper()).postDelayed({
                                checkNoData()
                            }, 100)
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
                user_type = UserStateManager.getUserProfile()?.user_type.toString(),
                token = UserStateManager.getBearerToken()
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
                    it as ArrayList<OrderStatusResponse.OrderStatus>,
                    dashboardViewModel.selectedOrderStatusFilter,
                    dashboardViewModel.selectedOrderStagesFilter,
                    1
                )
            }
        orderStatusBottomSheet.setOnOrderStatusListener(this)
        orderStatusBottomSheet.show(childFragmentManager, "filter-picker")
    }

    private fun setOrderStage() {
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

        val orderStagesBottomSheet =
            dashboardViewModel.orderStagesArray.let {
                OrderStatusFilterBottomSheet.newInstance(
                    it as ArrayList<OrderStatusResponse.OrderStatus>,
                    dashboardViewModel.selectedOrderStatusFilter,
                    dashboardViewModel.selectedOrderStagesFilter,
                    2
                )
            }
        orderStagesBottomSheet.setOnOrderStatusListener(this)
        orderStagesBottomSheet.show(childFragmentManager, "filter-picker")
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.ivFilterStatus -> setOrderStatus()
            binding.ivFilterStag -> setOrderStage()
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
            R.id.tv_name -> {
                if (UserStateManager.getUserProfile()?.user_type.equals(
                        "admin",
                        ignoreCase = true
                    ) && UserStateManager.getUserProfile()?.permissions?.client.equals("1",ignoreCase = true)
                ) {
                    requireActivity().gotoActivity(
                        CustomerDetailActivity::class.java,
                        bundle = bundleOf(Constants.EXTRA_CUSTOMER to model.clientId),
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOrderStatusSelected(orderStatus: OrderStatusResponse.OrderStatus) {
        dashboardViewModel.selectedOrderStatusFilter = orderStatus.id.toString()
        orderListAdapter?.setItems(orderListArray.filter {
            it.statusId.equals(
                dashboardViewModel.selectedOrderStatusFilter,
                ignoreCase = true
            ) || dashboardViewModel.selectedOrderStatusFilter.isEmpty()
        } as ArrayList<OrdersResponse?>)
        Handler(Looper.getMainLooper()).postDelayed({
            checkNoData()
        }, 100)
//        callOrderListApi()
    }

    override fun onOrderStagesSelected(orderStatus: OrderStatusResponse.OrderStatus) {
        dashboardViewModel.selectedOrderStagesFilter = orderStatus.id.toString()
        orderListAdapter?.setItems(orderListArray.filter {
            it.stageId.equals(
                dashboardViewModel.selectedOrderStagesFilter,
                ignoreCase = true
            ) || dashboardViewModel.selectedOrderStagesFilter.isEmpty()
        } as ArrayList<OrdersResponse?>)
        Handler(Looper.getMainLooper()).postDelayed({
            checkNoData()
        }, 100)
    }
}