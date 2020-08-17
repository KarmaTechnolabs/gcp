package com.app.masterproject.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.app.masterproject.R;
import com.app.masterproject.adapter.StateGenericAdapter;
import com.app.masterproject.base.BaseActivity;
import com.app.masterproject.databinding.ActivityStatePickerBinding;
import com.app.masterproject.listeners.OnItemClickListener;
import com.app.masterproject.model.StateGenericModel;
import java.util.ArrayList;

public class StatePickerActivity extends BaseActivity implements View.OnClickListener {

    public static final String OPTIONS_DATA = "OPTIONS_DATA";
    public static final String LAST_POSITION_DATA = "LAST_POSITION";
    public static final String OPTIONS_TITLE = "OPTIONS_TITLE";
    ActivityStatePickerBinding binding;
    private ArrayList<StateGenericModel> list;
    private StateGenericAdapter adapter;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_state_picker);
        initView();
    }
    private void initView(){
        if (getIntent().getExtras()!=null){

            list = getIntent().getParcelableArrayListExtra(OPTIONS_DATA);
            title = getIntent().getStringExtra(OPTIONS_TITLE);
        }
        binding.rvOptionList.setLayoutManager(new LinearLayoutManager(this));
        binding.tvTitle.setVisibility(View.VISIBLE);
        binding.tvTitle.setText(title);

        binding.svList.setVisibility(View.VISIBLE);
        adapter = new StateGenericAdapter(this);
        binding.rvOptionList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.rvOptionList.setAdapter(adapter);
        adapter.setItems(list);
        binding.ivClose.setOnClickListener(this);

        binding.svList.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


        adapter.setOnItemClickListener(new OnItemClickListener<StateGenericModel>() {
            //@Override
            public void onItemClick(View view, StateGenericModel object, int position) {

                object.setSelected(true);

                Intent intent = new Intent();
                intent.putExtra(OPTIONS_DATA,object);
                intent.putExtra(LAST_POSITION_DATA,adapter.getLastSelectedPosition());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
