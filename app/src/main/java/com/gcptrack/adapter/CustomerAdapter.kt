package com.gcptrack.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gcptrack.R
import com.gcptrack.api.responsemodel.CustomersResponse
import com.gcptrack.databinding.ItemListCustomerBinding
import com.gcptrack.listeners.ItemClickListener
import java.util.*

class CustomerAdapter(context: Context?) :
    BaseAdapter<CustomersResponse?, CustomerAdapter.ViewHolder?>(context),
    Filterable {
    var filteredCustomerList = ArrayList<CustomersResponse?>()
    var originalData = ArrayList<CustomersResponse?>()
    private var clickListener: ItemClickListener<CustomersResponse>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListCustomerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_list_customer, parent, false
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
                    val filterData: MutableList<CustomersResponse?> = ArrayList()
                    for (model in list) {
                        if (model?.companyName?.lowercase(Locale.getDefault())
                                ?.contains(charSequence)!!
                        ) {
                            filterData.add(model)
                        }
                    }
                    results.values = filterData
                    results.count = filterData.size
                }
                return results
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredCustomerList = filterResults.values as ArrayList<CustomersResponse?>
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredCustomerList.size
    }

    override fun getListItem(position: Int): CustomersResponse {
        return filteredCustomerList[position]!!
    }

    inner class ViewHolder(binding: ItemListCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemListCustomerBinding = binding
        fun setBinding(model: CustomersResponse?) {
            binding.model = model
            binding.clickListener = clickListener
            binding.position = absoluteAdapterPosition
            binding.executePendingBindings()
        }

        init {
            binding.root.setOnClickListener(View.OnClickListener { view ->
                if (onItemClickListener != null) {
                    val model: CustomersResponse = getListItem(bindingAdapterPosition)
                    val selectedPos = bindingAdapterPosition
                    notifyDataSetChanged()
                    onItemClickListener.onItemClick(view, model, selectedPos)
                }
            })
        }
    }

    init {
        filteredCustomerList = list
        originalData = list
    }

    fun setClickListener(clickListener: ItemClickListener<CustomersResponse>) {
        this.clickListener = clickListener
    }
}
