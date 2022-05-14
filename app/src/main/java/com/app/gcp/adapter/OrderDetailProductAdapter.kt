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
import com.app.gcp.databinding.ItemListOrdersBinding
import com.app.gcp.databinding.ItemListProductBinding
import com.app.gcp.listeners.ItemClickListener
import java.util.*

class OrderDetailProductAdapter(context: Context?) :
    BaseAdapter<OrdersDetailsResponse.Product?, OrderDetailProductAdapter.ViewHolder?>(context){
    var orderProductList = ArrayList<OrdersDetailsResponse.Product?>()
    private var clickListener: ItemClickListener<OrdersDetailsResponse.Product>? = null
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
            binding.clickListener = clickListener
            binding.position = absoluteAdapterPosition
            binding.executePendingBindings()
        }

        init {
            binding.root.setOnClickListener(View.OnClickListener { view ->
                if (onItemClickListener != null) {
                    val model: OrdersDetailsResponse.Product = getListItem(bindingAdapterPosition)
                    val selectedPos = bindingAdapterPosition
                    notifyDataSetChanged()
                    onItemClickListener.onItemClick(view, model, selectedPos)
                }
            })
        }
    }

    init {
        orderProductList = list
    }

    fun setClickListener(clickListener: ItemClickListener<OrdersDetailsResponse.Product>) {
        this.clickListener = clickListener
    }
}
