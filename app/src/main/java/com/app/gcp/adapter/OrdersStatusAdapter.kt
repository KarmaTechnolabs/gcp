package com.app.gcp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.gcp.R
import com.app.gcp.api.responsemodel.OrderStatusResponse
import com.app.gcp.databinding.ItemListOrdersStatusBinding
import com.app.gcp.listeners.ItemClickListener
import java.util.*

class OrdersStatusAdapter(context: Context?) :
    BaseAdapter<OrderStatusResponse?, OrdersStatusAdapter.ViewHolder?>(context){
    private var orderListData = ArrayList<OrderStatusResponse?>()
    private var selectedStatus : String? = ""
    private var clickListener: ItemClickListener<OrderStatusResponse>? = null
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

    override fun getListItem(position: Int): OrderStatusResponse {
        return orderListData[position]!!
    }

    inner class ViewHolder(binding: ItemListOrdersStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemListOrdersStatusBinding = binding
        fun setBinding(model: OrderStatusResponse?) {
            binding.model = model
            binding.isSelected =  selectedStatus.equals(model?.id)
            binding.clickListener = clickListener
            binding.position = absoluteAdapterPosition
            binding.executePendingBindings()

//            binding.rbStatus.setOnClickListener(View.OnClickListener {view ->
//                if (!binding.rbStatus.isSelected) {
//                    binding.rbStatus.isSelected = true
//                    binding.rbStatus.isSelected = true
//                    onItemClickListener.onItemClick(view, getListItem(bindingAdapterPosition), bindingAdapterPosition)
//                } else {
//                    binding.rbStatus.isChecked = false
//                    binding.rbStatus.isChecked = false
//                }
//            })

        }

        init {
            binding.root.setOnClickListener(View.OnClickListener { view ->
                if (onItemClickListener != null) {
                    val model:OrderStatusResponse = getListItem(bindingAdapterPosition)
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

    fun setClickListener(clickListener: ItemClickListener<OrderStatusResponse>) {
        this.clickListener = clickListener
    }

    fun setSelectedStatus(selectedOrderStatusFilter: String?) {
        selectedStatus = selectedOrderStatusFilter
    }
}
