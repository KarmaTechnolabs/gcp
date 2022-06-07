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
import com.gcptrack.adapter.CustomerAdapter
import com.gcptrack.api.requestmodel.OrderListRequestModel
import com.gcptrack.api.responsemodel.CustomersResponse
import com.gcptrack.base.BaseFragment
import com.gcptrack.custom.gotoActivity
import com.gcptrack.databinding.FragmentCustomersBinding
import com.gcptrack.listeners.ItemClickListener
import com.gcptrack.ui.activities.CustomerDetailActivity
import com.gcptrack.utils.Constants
import com.gcptrack.utils.UserStateManager
import com.gcptrack.viewmodel.DashBoardViewModel

class CustomersFragment : BaseFragment(),
    ItemClickListener<CustomersResponse> {

    private var _binding: FragmentCustomersBinding? = null
    private val customerListArray = mutableListOf<CustomersResponse>()
    private var customerListAdapter: CustomerAdapter? = null
    private val dashboardViewModel by activityViewModels<DashBoardViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomersBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this

        binding.swipeRefresh.setOnRefreshListener {
            callCustomerListApi()
            binding.swipeRefresh.isRefreshing = true
        }

        dashboardViewModel.customerListResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(
                    response, isShowProgress = true,
                    successListener = object : (List<CustomersResponse>, String) -> Unit {
                        override fun invoke(it: List<CustomersResponse>, message: String) {
//                            showToast(message)
                            customerListArray.clear()
                            customerListArray.addAll(it)
                            customerListAdapter?.setItems(customerListArray as ArrayList<CustomersResponse?>)
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

        customerListAdapter = CustomerAdapter(activity)
        customerListAdapter?.setClickListener(this)
        binding.rvSearchOrder.adapter = customerListAdapter

        callCustomerListApi()

        binding.svSearchCustomer.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                customerListAdapter?.filter?.filter(query)
                Handler(Looper.getMainLooper()).postDelayed({
                    checkNoData()
                }, 100)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                customerListAdapter?.filter?.filter(newText)
                Handler(Looper.getMainLooper()).postDelayed({
                    checkNoData()
                }, 100)
                return false
            }
        })

    }

    private fun callCustomerListApi() {
        dashboardViewModel.callCustomerListAPI(
            OrderListRequestModel(
                user_type = UserStateManager.getUserProfile()?.user_type.toString(),
                token = UserStateManager.getBearerToken()
            )
        )
    }

    private fun checkNoData() {
        if (customerListAdapter?.itemCount!! > 0) {
            binding.tvNoData.visibility = View.GONE
        } else {
            binding.tvNoData.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(viewIdRes: Int, model: CustomersResponse, position: Int) {
        when (viewIdRes) {
            R.id.mcv_main -> {
                requireActivity().gotoActivity(
                    CustomerDetailActivity::class.java,
                    bundle = bundleOf(Constants.EXTRA_CUSTOMER to model.id),
                    needToFinish = false
                )
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}