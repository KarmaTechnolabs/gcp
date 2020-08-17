package com.app.masterproject.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.app.masterproject.R
import com.app.masterproject.databinding.FragmentSelectMediaListBinding
import com.app.masterproject.listeners.OnItemClickListener
import com.app.masterproject.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectMediaBottomSheetDialog : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var binding: FragmentSelectMediaListBinding
    private var imageChooserListener: OnItemClickListener<Int>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_select_media_list,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCamera.setOnClickListener(this)
        binding.tvGallery.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id!!) {
            R.id.tv_camera -> {
                dismiss()
                imageChooserListener?.onItemClick(v, Constants.PICK_IMAGE_FROM_CAMERA, -1)
            }
            R.id.tv_gallery -> {
                dismiss()
                imageChooserListener?.onItemClick(v, Constants.PICK_IMAGE_FROM_GALLERY, -1)
            }
        }
    }

    fun setImageChooseListener(itemClickListener: OnItemClickListener<Int>) {
        this.imageChooserListener = itemClickListener
    }
}
