package com.gcptrack.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gcptrack.R
import com.gcptrack.api.responsemodel.OrderStatusResponse
import com.gcptrack.databinding.ItemListOrdersStatusBinding
import com.gcptrack.listeners.ItemClickListener

class OrdersFilterAdapter(context: Context?) :
    BaseAdapter<OrderStatusResponse.OrderStatus?, OrdersFilterAdapter.ViewHolder?>(context) {
    private var orderListData = ArrayList<OrderStatusResponse.OrderStatus?>()
    private var selectedFilter: String? = ""
    private var clickListener: ItemClickListener<OrderStatusResponse.OrderStatus>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListOrdersStatusBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_list_orders_status, parent, false
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
        return orderListData.size
    }

    override fun getListItem(position: Int): OrderStatusResponse.OrderStatus {
        return orderListData[position]!!
    }

    inner class ViewHolder(binding: ItemListOrdersStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemListOrdersStatusBinding = binding
        fun setBinding(model: OrderStatusResponse.OrderStatus?) {
            binding.model = model
            binding.isSelected = selectedFilter.equals(model?.id)
            binding.clickListener = clickListener
            binding.position = absoluteAdapterPosition
            binding.executePendingBindings()
        }

        init {
            binding.root.setOnClickListener(View.OnClickListener { view ->
                if (onItemClickListener != null) {
                    val model: OrderStatusResponse.OrderStatus = getListItem(bindingAdapterPosition)
                    val selectedPos = bindingAdapterPosition
                    notifyDataSetChanged()
                    onItemClickListener.onItemClick(view, model, selectedPos)
                }
            })
        }
    }

    init {
        orderListData = list
    }

    fun setClickListener(clickListener: ItemClickListener<OrderStatusResponse.OrderStatus>) {
        this.clickListener = clickListener
    }

    fun setSelectedFilter(selectedOrderStatusFilter: String?) {
        selectedFilter = selectedOrderStatusFilter
    }

}
