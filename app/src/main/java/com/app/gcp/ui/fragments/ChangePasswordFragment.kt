package com.app.gcp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.gcp.base.BaseFragment
import com.app.gcp.databinding.FragmentChangePasswordBinding
import com.app.gcp.databinding.FragmentFaqsBinding
import com.app.gcp.viewmodel.DashBoardViewModel

class ChangePasswordFragment : BaseFragment() {

    private lateinit var fAQsViewModel: DashBoardViewModel
    private var _binding: FragmentChangePasswordBinding? = null

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

        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}