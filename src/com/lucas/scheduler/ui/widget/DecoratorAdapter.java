package com.lucas.scheduler.ui.widget;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
public class DecoratorAdapter extends BaseAdapter {
    protected ListAdapter mOrigAdapter;

    public DecoratorAdapter(ListAdapter adapter) {
        mOrigAdapter = adapter;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        // TODO Auto-generated method stub
        mOrigAdapter.registerDataSetObserver(observer);
        super.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        // TODO Auto-generated method stub
        mOrigAdapter.unregisterDataSetObserver(observer);
        super.unregisterDataSetObserver(observer);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mOrigAdapter.getCount();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mOrigAdapter.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return mOrigAdapter.getItemId(position);
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return mOrigAdapter.hasStableIds();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return mOrigAdapter.getView(position, convertView, parent);
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return mOrigAdapter.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return mOrigAdapter.getViewTypeCount();
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return mOrigAdapter.isEmpty();
    }
}
