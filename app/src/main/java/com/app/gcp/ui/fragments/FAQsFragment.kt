package com.app.gcp.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.gcp.R
import com.app.gcp.adapter.OrdersAdapter
import com.app.gcp.api.responsemodel.OrdersResponse
import com.app.gcp.base.BaseFragment
import com.app.gcp.databinding.FragmentFaqsBinding
import com.app.gcp.viewmodel.DashBoardViewModel
import java.util.ArrayList

class FAQsFragment : BaseFragment() {

    private lateinit var fAQsViewModel: DashBoardViewModel
    private var _binding: FragmentFaqsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fAQsViewModel =
            ViewModelProvider(this).get(DashBoardViewModel::class.java)

        _binding = FragmentFaqsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textSlideshow
//        fAQsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.webviewFaqs.loadUrl("https://www.ets.org/test_link/about/faq/")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}