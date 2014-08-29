package com.lucas.scheduler.ui;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lucas.scheduler.data.Param;

public class ParamsSpinnerAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Param> mParams;
    public ParamsSpinnerAdapter(ArrayList<Param> params,Context context){
        mParams = params;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(mParams == null){
            return 0;
        }
        return mParams.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        if(mParams == null){
            return null;
        }
        return mParams.get(position);
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        if(mParams == null){
            return 0;
        }
        return mParams.get(position).getId();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = convertView;
        if(v == null){
            v = mInflater.inflate(android.R.layout.simple_spinner_item, null);
        }
        bindView(v, mParams.get(position));
        return v;
    }

    protected void bindView(View v,Param param){
        if(v instanceof TextView){
            TextView tv = (TextView)v;
            tv.setText(param.getTitle());
        }
    }
    public void swapData(ArrayList<Param> params){
        mParams = params;
        notifyDataSetChanged();
    }
}
