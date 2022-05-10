package com.app.gcp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.gcp.R;
import com.app.gcp.api.responsemodel.OrderStatusResponse;

import java.util.List;

public class OrderStatusAdapter extends ArrayAdapter<OrderStatusResponse> {

    private LayoutInflater flater;
    private Context context;

    public OrderStatusAdapter(Activity context, int resouceId, int textviewId, List<OrderStatusResponse> list){
        super(context,resouceId,textviewId, list);
        this.context = context;
        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView,position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    private View rowview(View convertView , int position){

        OrderStatusResponse rowItem = getItem(position);

        viewHolder holder ;
        View rowview = convertView;

        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.list_item_order_status_spinner, null, false);
            holder.txtTitle = (TextView) rowview.findViewById(R.id.tv_order_status);
            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }
        holder.txtTitle.setText(rowItem.getTitle());

        return rowview;
    }

    private class viewHolder{
        TextView txtTitle;
    }
}