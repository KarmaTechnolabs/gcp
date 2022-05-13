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
import com.app.gcp.api.responsemodel.OrdersResponse
import com.app.gcp.databinding.ItemListOrdersBinding
import com.app.gcp.listeners.ItemClickListener
import java.util.*

class OrdersAdapter(context: Context?) :
    BaseAdapter<OrdersResponse?, OrdersAdapter.ViewHolder?>(context),
    Filterable {
    var filteredOrderList = ArrayList<OrdersResponse?>()
    var originalData = ArrayList<OrdersResponse?>()
    private var clickListener: ItemClickListener<OrdersResponse>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListOrdersBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_list_orders, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getListItem(position)
        if (model != null) {
            holder.setBinding(model)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val results = FilterResults()
                if (charSequence == null || charSequence.isEmpty()) {
                    results.values = originalData
                    results.count = originalData.size
                } else {
                    val filterData: MutableList<OrdersResponse?> = ArrayList()
                    for (model in list) {
                        if (model?.trackingNumber?.lowercase(Locale.getDefault())?.contains(charSequence)!!) {
                            filterData.add(model)
                        }
                    }
                    results.values = filterData
                    results.count = filterData.size
                }
                return results
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredOrderList = filterResults.values as ArrayList<OrdersResponse?>
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredOrderList.size
    }

    override fun getListItem(position: Int): OrdersResponse {
        return filteredOrderList[position]!!
    }

    inner class ViewHolder(binding: ItemListOrdersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemListOrdersBinding = binding
        fun setBinding(model: OrdersResponse?) {
            binding.model = model
            binding.clickListener = clickListener
            binding.position = absoluteAdapterPosition
            binding.executePendingBindings()
        }

        init {
            binding.root.setOnClickListener(View.OnClickListener { view ->
                if (onItemClickListener != null) {
                    val model:OrdersResponse = getListItem(bindingAdapterPosition)
                    val selectedPos = bindingAdapterPosition
                    notifyDataSetChanged()
                    onItemClickListener.onItemClick(view, model, selectedPos)
                }
            })
        }
    }

    init {
        filteredOrderList = list
        originalData = list
    }

    fun setClickListener(clickListener: ItemClickListener<OrdersResponse>) {
        this.clickListener = clickListener
    }
}
