package com.gcptrack.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.gcptrack.base.BaseFragment
import com.gcptrack.databinding.FragmentFaqsBinding
import com.gcptrack.viewmodel.DashBoardViewModel


class FAQsFragment : BaseFragment(), View.OnClickListener {

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
            ViewModelProvider(this)[DashBoardViewModel::class.java]

        _binding = FragmentFaqsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.clickListener = this
        binding.webview.loadUrl("file:///android_asset/faq.html")
        binding.webview.settings.javaScriptEnabled = true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View?) {
        when (view) {

//            binding.labelTypeIcon, binding.labelTypeTitle -> {
//                if (binding.labelTypeIcon.isChecked) {
//                    binding.labelTypeDescription.visibility = View.VISIBLE
//                } else {
//                    binding.labelTypeDescription.visibility = View.GONE
//                }
//            }
//            binding.labelOfficeIcon, binding.labelOfficeTitle -> {
//                if (binding.labelOfficeIcon.isChecked) {
//                    binding.labelOfficeDescription.visibility = View.VISIBLE
//                } else {
//                    binding.labelOfficeDescription.visibility = View.GONE
//                }
//            }
//            binding.labelServeIcon, binding.labelServeTitle -> {
//                if (binding.labelServeIcon.isChecked) {
//                    binding.labelServeDescription.visibility = View.VISIBLE
//                } else {
//                    binding.labelServeDescription.visibility = View.GONE
//                }
//            }
//            binding.labelCapacityIcon, binding.labelCapacityTitle -> {
//                if (binding.labelCapacityIcon.isChecked) {
//                    binding.labelCapacityDescription.visibility = View.VISIBLE
//                } else {
//                    binding.labelCapacityDescription.visibility = View.GONE
//                }
//            }
//            binding.labelCapabilitiesIcon, binding.labelCapabilitiesTitle -> {
//                if (binding.labelCapabilitiesIcon.isChecked) {
//                    binding.labelCapabilitiesDescription.visibility = View.VISIBLE
//                } else {
//                    binding.labelCapabilitiesDescription.visibility = View.GONE
//                }
//            }
//            binding.labelQueriesIcon, binding.labelQueriesTitle -> {
//                if (binding.labelQueriesIcon.isChecked) {
//                    binding.labelQueriesDescription.visibility = View.VISIBLE
//                } else {
//                    binding.labelQueriesDescription.visibility = View.GONE
//                }
//            }
        }
    }
}