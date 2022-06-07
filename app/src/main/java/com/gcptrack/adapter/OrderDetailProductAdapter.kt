package com.gcptrack.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gcptrack.R
import com.gcptrack.api.responsemodel.OrdersDetailsResponse
import com.gcptrack.databinding.ItemListProductBinding
import java.util.*

class OrderDetailProductAdapter(context: Context?) :
    BaseAdapter<OrdersDetailsResponse.Product?, OrderDetailProductAdapter.ViewHolder?>(context){
    var orderProductList = ArrayList<OrdersDetailsResponse.Product?>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_list_product, parent, false
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

    override fun getListItem(position: Int): OrdersDetailsResponse.Product {
        return orderProductList[position]!!
    }

    inner class ViewHolder(binding: ItemListProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemListProductBinding = binding
        fun setBinding(model: OrdersDetailsResponse.Product?) {
            binding.model = model
            binding.executePendingBindings()
        }
    }

    init {
        orderProductList = list
    }

}
