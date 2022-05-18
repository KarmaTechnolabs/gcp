package com.app.gcp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.gcp.R
import com.app.gcp.api.responsemodel.TrackOrderResponse
import com.app.gcp.databinding.ItemListTrackOrdersStatusBinding
import com.app.gcp.listeners.ItemClickListener

class TrackOrderStatusAdapter(context: Context?) :
    BaseAdapter<TrackOrderResponse.History?, TrackOrderStatusAdapter.ViewHolder?>(context) {
    private val TAG = TrackOrderStatusAdapter::class.java.simpleName
    var orderStatusList = ArrayList<TrackOrderResponse.History?>()
    private var clickListener: ItemClickListener<TrackOrderResponse.History>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListTrackOrdersStatusBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_list_track_orders_status,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getListItem(position)
        if (model != null) {
            holder.setBinding(model)
        }
    }

    override fun getItemCount(): Int {
        return orderStatusList.size
    }

    override fun getListItem(position: Int): TrackOrderResponse.History {
        return orderStatusList[position]!!
    }

    inner class ViewHolder(binding: ItemListTrackOrdersStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemListTrackOrdersStatusBinding = binding
        fun setBinding(model: TrackOrderResponse.History?) {
            binding.model = model
            binding.clickListener = clickListener
            binding.isFirst = (absoluteAdapterPosition == 0)
            binding.isLast = (absoluteAdapterPosition == orderStatusList.size.minus(1))
            Log.d(TAG, "setBinding() called with: model = "+orderStatusList.size.minus(1)+"islast: "+binding.isLast+"position: "+absoluteAdapterPosition)
            binding.executePendingBindings()
        }
    }

    init {
        orderStatusList = list
    }

}
