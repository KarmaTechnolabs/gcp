package com.app.gcp.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.app.gcp.R
import com.app.gcp.adapter.OrdersAdapter
import com.app.gcp.api.responsemodel.OrdersResponse
import com.app.gcp.base.BaseFragment
import com.app.gcp.databinding.FragmentOrdersBinding
import com.app.gcp.listeners.ItemClickListener
import com.app.gcp.viewmodel.OrdersViewModel
import java.util.ArrayList

class OrdersFragment : BaseFragment(), View.OnClickListener,
    ItemClickListener<OrdersResponse> {

    private lateinit var ordersViewModel: OrdersViewModel
    private var _binding: FragmentOrdersBinding? = null
    val gameListArray = mutableListOf<OrdersResponse>()
    private var gameListAdapter: OrdersAdapter? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ordersViewModel =
            ViewModelProvider(this).get(OrdersViewModel::class.java)

        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        ordersViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.clickListener = this

        for (i in 1..10) {
            gameListArray.add(OrdersResponse(resources.getString(R.string.order),resources.getString(R.string.order_date),resources.getString(R.string.pending___),resources.getString(R.string.order_name)));
        }


        gameListAdapter = OrdersAdapter(activity)
        gameListAdapter?.setClickListener(this)
        binding.rvSearchGame.adapter=gameListAdapter
        gameListAdapter?.setItems(gameListArray as ArrayList<OrdersResponse?>)
        checkNoData()

//        binding.svSearchGame.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                gameListAdapter?.filter?.filter(query)
//                Handler().postDelayed({
//                    checkNoData()
//                }, 100)
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                gameListAdapter?.filter?.filter(newText)
//                Handler().postDelayed({
//                    checkNoData()
//                }, 100)
//                return false
//            }
//        })

    }

    private fun checkNoData() {
        if (gameListAdapter?.itemCount!! > 0) {
            binding.tvNoData.visibility = View.GONE
        } else {
            binding.tvNoData.visibility = View.VISIBLE
        }
    }

    override fun onClick(view: View?) {

    }

    override fun onItemClick(viewIdRes: Int, model: OrdersResponse, position: Int) {
        when (viewIdRes) {

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}