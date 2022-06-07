package com.gcptrack.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gcptrack.R
import com.gcptrack.databinding.ListItemHomeSliderBinding
import com.gcptrack.model.HomeSliderModel

class HomeSliderAdapter(
    private var data: List<HomeSliderModel>,
    private val needToShowTitle: Boolean = true
) :
    RecyclerView.Adapter<HomeSliderAdapter.SliderVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderVH {
        val v: ListItemHomeSliderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_home_slider,
            parent,
            false
        )
        return SliderVH(v)
    }

    override fun onBindViewHolder(holder: SliderVH, position: Int) {
        val resultsItem = data[position]
        holder.binding.model = resultsItem
        holder.binding.needToShowTitle = needToShowTitle
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class SliderVH(val binding: ListItemHomeSliderBinding) :
        RecyclerView.ViewHolder(binding.root)
}