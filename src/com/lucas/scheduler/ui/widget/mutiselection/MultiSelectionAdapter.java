package com.lucas.scheduler.ui.widget.mutiselection;

import java.util.Iterator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListAdapter;

import com.lucas.scheduler.ui.widget.DecoratorAdapter;
import com.lucas.scheduler.utilities.Log;

public class MultiSelectionAdapter extends DecoratorAdapter implements MultiSelectAble{
    private Context mContext;
    private Mode mMultiSelectMode = Mode.NORMAL;
    private Helper mMultiSelectHelper;
    public MultiSelectionAdapter(Context context,ListAdapter adapter) {
        super(adapter);
        // TODO Auto-generated constructor stub
        mContext = context;
        mMultiSelectHelper = new Helper(this);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            v = new MultiSelectItemView(mContext,mOrigAdapter.getView(position, convertView, parent));
        }else {
            if(v instanceof MultiSelectItemView){
                View orig = mOrigAdapter.getView(position, ((MultiSelectItemView) convertView).getOrigView(), parent);
                ((MultiSelectItemView) convertView).setOrigView(orig);
            }else{
                Log.e(Log.FLAG_UI, "error convertView must be a MultiSelectItemView");
                return null;
            }
        }
        CheckBox cb = ((MultiSelectItemView)v).getCheckBox();
        setupCheckBox(mMultiSelectMode,position,cb);
        return v;
    }
    
    private void setupCheckBox(Mode mode,int pos,CheckBox cb){
        if(mode == Mode.NORMAL){
            cb.setVisibility(View.GONE);
        }else if(mode == Mode.MULTISELECT){
            cb.setVisibility(View.VISIBLE);
            if(mMultiSelectHelper.isSelected(pos)){
                cb.setChecked(true);
            }else{
                cb.setChecked(false);
            }
        }
    }

    @Override
    public void selectDeselectAll(boolean select) {
        // TODO Auto-generated method stub
        mMultiSelectHelper.selectDeselectAll(select);
    }

    @Override
    public void setMutiSelectionMode(Mode mode) {
        // TODO Auto-generated method stub
        if(mMultiSelectMode != mode){
            mMultiSelectMode = mode;
            mMultiSelectHelper.selectDeselectAll(false);
            notifyDataSetChanged();
        }
    }

    @Override
    public Mode getMode() {
        // TODO Auto-generated method stub
        return mMultiSelectMode;
    }

    @Override
    public Iterator<Integer> getSelectedIterator() {
        // TODO Auto-generated method stub
        return mMultiSelectHelper.getSelectedIterator();
    }

    @Override
    public boolean toggleSelected(int index) {
        // TODO Auto-generated method stub
        boolean ret =  mMultiSelectHelper.toggle(index);
        notifyDataSetChanged();
        return ret;
    }

}
