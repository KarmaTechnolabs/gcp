package com.app.gcp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.gcp.R
import com.app.gcp.api.responsemodel.OrdersDetailsResponse
import com.app.gcp.api.responsemodel.OrdersResponse
import com.app.gcp.databinding.ItemListOrderStageBinding
import com.app.gcp.databinding.ItemListOrdersBinding
import com.app.gcp.databinding.ItemListProductBinding
import com.app.gcp.listeners.ItemClickListener
import java.util.*

class OrderDetailStageAdapter(context: Context?) :
    BaseAdapter<OrdersDetailsResponse.OrderStagesHistory?, OrderDetailStageAdapter.ViewHolder?>(context){
    var orderProductList = ArrayList<OrdersDetailsResponse.OrderStagesHistory?>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListOrderStageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_list_order_stage, parent, false
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
        return orderProductList.size
    }

    override fun getListItem(position: Int): OrdersDetailsResponse.OrderStagesHistory {
        return orderProductList[position]!!
    }

    inner class ViewHolder(binding: ItemListOrderStageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemListOrderStageBinding = binding
        fun setBinding(model: OrdersDetailsResponse.OrderStagesHistory?) {
            binding.model = model
            binding.executePendingBindings()
        }
    }

    init {
        orderProductList = list
    }
}
