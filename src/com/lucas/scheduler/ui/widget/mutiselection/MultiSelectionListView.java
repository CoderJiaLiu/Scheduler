package com.lucas.scheduler.ui.widget.mutiselection;

import java.util.Iterator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MultiSelectionListView extends ListView implements MultiSelectAble{
    private Context mContext;
    private MultiSelectionAdapter mAdapter;
    private OnItemClickListener mL;
    public MultiSelectionListView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public MultiSelectionListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public MultiSelectionListView(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        mContext = context;
        setOnItemClickListener(null);
    }

    public void setAdapter(ListAdapter adapter){
        // TODO Auto-generated method stub
        mAdapter = new MultiSelectionAdapter(mContext,adapter);
        super.setAdapter(mAdapter);
    }

    @Override
    public void selectDeselectAll(boolean select) {
        // TODO Auto-generated method stub
        mAdapter.selectDeselectAll(select);
    }

    @Override
    public void setMutiSelectionMode(Mode mode) {
        // TODO Auto-generated method stub
        mAdapter.setMutiSelectionMode(mode);
    }

    @Override
    public Mode getMode() {
        // TODO Auto-generated method stub
        return mAdapter.getMode();
    }

    @Override
    public Iterator<Integer> getSelectedIterator() {
        // TODO Auto-generated method stub
        return mAdapter.getSelectedIterator();
    }

    @Override
    public boolean toggleSelected(int index) {
        // TODO Auto-generated method stub
        return mAdapter.toggleSelected(index);
    }

    @Override
    public void setOnItemClickListener(
            android.widget.AdapterView.OnItemClickListener listener) {
        // TODO Auto-generated method stub
        mL = listener;
        OnItemClickListener l = new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // TODO Auto-generated method stub
                if(getMode() == Mode.MULTISELECT){
                    toggleSelected(position);
                    return;
                }
                if(mL != null){
                    mL.onItemClick(parent, view, position, id);
                }
            }
        };
        super.setOnItemClickListener(l);
    }
}
