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
    var filteredGameList = ArrayList<OrdersResponse?>()
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
                if (charSequence == null || charSequence.length == 0) {
                    results.values = originalData
                    results.count = originalData.size
                } else {
                    val filterData: MutableList<OrdersResponse?> = ArrayList()
                    for (model in list) {
                        if (model!!.name.lowercase(Locale.getDefault()).contains(charSequence)) {
                            filterData.add(model)
                        }
                    }
                    results.values = filterData
                    results.count = filterData.size
                }
                return results
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredGameList = filterResults.values as ArrayList<OrdersResponse?>
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredGameList.size
    }

    override fun getListItem(position: Int): OrdersResponse {
        return filteredGameList[position]!!
    }

    inner class ViewHolder(binding: ItemListOrdersBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: ItemListOrdersBinding
        fun setBinding(model: OrdersResponse?) {
            binding.setModel(model)
            binding.clickListener = clickListener
            binding.position = absoluteAdapterPosition
            binding.executePendingBindings()
        }

        init {
            this.binding = binding
            binding.getRoot().setOnClickListener(View.OnClickListener { view ->
                if (onItemClickListener != null) {
                    val model:OrdersResponse = getListItem(adapterPosition)
                    val selectedPos = adapterPosition
                    notifyDataSetChanged()
                    onItemClickListener.onItemClick(view, model, selectedPos)
                }
            })
        }
    }

    init {
        filteredGameList = list
        originalData = list
    }

    fun setClickListener(clickListener: ItemClickListener<OrdersResponse>) {
        this.clickListener = clickListener
    }
}
