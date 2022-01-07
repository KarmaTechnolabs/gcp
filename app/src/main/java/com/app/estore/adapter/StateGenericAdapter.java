package com.app.estore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.estore.R;
import com.app.estore.databinding.ListItemStateBinding;
import com.app.estore.model.StateGenericModel;
import java.util.ArrayList;
import java.util.List;

public class StateGenericAdapter extends BaseAdapter<StateGenericModel, StateGenericAdapter.ViewHolder> implements Filterable {

    public ArrayList<StateGenericModel> filteredCityList = new ArrayList<>();
    public ArrayList<StateGenericModel> originalData= new ArrayList<>();
    private int lastSelectedPosition = -1;

    public StateGenericAdapter(Context context) {
        super(context);
        this.filteredCityList = getList();
        this.originalData = getList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemStateBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.list_item_state, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StateGenericModel stateGenericModel = getListItem(position);
        if (stateGenericModel != null) {
            holder.setBinding(stateGenericModel);

            if (stateGenericModel.isSelected())
                lastSelectedPosition = position;
            /*holder.binding.tvTitle.setText(bottomSheetOption.getTitle());
            if(bottomSheetOption.isSelected()){
                holder.binding.tvTitle.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
            }*/
        }
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                if(charSequence==null || charSequence.length() == 0){
                    results.values = originalData;
                    results.count = originalData.size();
                }else {
                    List<StateGenericModel> filterData = new ArrayList<>();
                    for (StateGenericModel bottomSheetOption : getList()) {
                        if(bottomSheetOption.getTitle().toLowerCase().contains(charSequence)){
                            filterData.add(bottomSheetOption);
                        }
                    }
                    results.values = filterData;
                    results.count = filterData.size();
                }
                return results;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredCityList = (ArrayList<StateGenericModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    @Override
    public int getItemCount() {
        return filteredCityList.size();
    }

    @Override
    public StateGenericModel getListItem(int position) {
        return filteredCityList.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ListItemStateBinding binding;
        public ViewHolder(ListItemStateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        StateGenericModel object = getListItem(getAdapterPosition());
                        int selectedPos = getAdapterPosition();

                        getListItem(getAdapterPosition()).setSelected(true);
                        getListItem(getAdapterPosition()).setSelectedPosition(getAdapterPosition());

                        if (lastSelectedPosition!= -1)
                            getListItem(lastSelectedPosition).setSelected(false);


                        notifyDataSetChanged();

                        onItemClickListener.onItemClick(view, object, selectedPos);
                    }
                }
            });
        }
        public void setBinding(StateGenericModel model){
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }
    public int getLastSelectedPosition(){
        return lastSelectedPosition;
    }
}
