package com.app.estore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.estore.R
import com.app.estore.databinding.ItemIntroductionPageBinding
import com.app.estore.model.IntroductionModel

class IntroductionPageAdapter(private val data: List<IntroductionModel>) :
    RecyclerView.Adapter<IntroductionPageAdapter.IntroductionVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroductionVH {
        val v: ItemIntroductionPageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_introduction_page,
            parent,
            false
        )
        return IntroductionVH(v)
    }

    override fun onBindViewHolder(holder: IntroductionVH, position: Int) {
        val resultsItem = data[position]
        holder.binding.model = resultsItem
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class IntroductionVH(val binding: ItemIntroductionPageBinding) :
        RecyclerView.ViewHolder(binding.root)
}