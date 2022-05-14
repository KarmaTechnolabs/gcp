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
import com.app.gcp.databinding.ItemListAdditionalDetailsBinding
import com.app.gcp.databinding.ItemListOrdersBinding
import com.app.gcp.databinding.ItemListProductBinding
import com.app.gcp.listeners.ItemClickListener
import java.util.*

class OrderDetailAdditionalDetailsAdapter(context: Context?) :
    BaseAdapter<OrdersDetailsResponse.CustomField?, OrderDetailAdditionalDetailsAdapter.ViewHolder?>(context){
    var orderProductList = ArrayList<OrdersDetailsResponse.CustomField?>()
    private var clickListener: ItemClickListener<OrdersDetailsResponse.CustomField>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListAdditionalDetailsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_list_additional_details, parent, false
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

    override fun getListItem(position: Int): OrdersDetailsResponse.CustomField {
        return orderProductList[position]!!
    }

    inner class ViewHolder(binding: ItemListAdditionalDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemListAdditionalDetailsBinding = binding
        fun setBinding(model: OrdersDetailsResponse.CustomField?) {
            binding.model = model
            binding.clickListener = clickListener
            binding.position = absoluteAdapterPosition
            binding.executePendingBindings()
        }

        init {
            binding.root.setOnClickListener(View.OnClickListener { view ->
                if (onItemClickListener != null) {
                    val model: OrdersDetailsResponse.CustomField = getListItem(bindingAdapterPosition)
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

    fun setClickListener(clickListener: ItemClickListener<OrdersDetailsResponse.CustomField>) {
        this.clickListener = clickListener
    }
}
