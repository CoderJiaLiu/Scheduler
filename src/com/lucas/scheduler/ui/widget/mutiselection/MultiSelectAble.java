package com.lucas.scheduler.ui.widget.mutiselection;

import java.util.HashSet;
import java.util.Iterator;

import android.widget.Adapter;

public interface MultiSelectAble {

    public static enum Mode {
        NORMAL, MULTISELECT
    }

    public void selectDeselectAll(boolean select);
    
    public void setMutiSelectionMode(Mode mode);
    
    public Mode getMode();
    
    public Iterator<Integer> getSelectedIterator();
    
    public boolean toggleSelected(int index);
    
    public class Helper {
        private Adapter mAdapter;
        private HashSet<Integer> mSelected = new HashSet<Integer>();

        public Helper(Adapter adapter) {
            mAdapter = adapter;
        }

        public boolean toggle(int pos) {
            if (mSelected.contains(pos)) {
                mSelected.remove(pos);
                return false;
            } else {
                mSelected.add(pos);
                return true;
            }
        }

        public void selectDeselectAll(boolean select) {
            if (select) {
                for (int i = 0; i < mAdapter.getCount(); i++) {
                    if (!mSelected.contains(i)) {
                        mSelected.add(i);
                    }
                }
            } else {
                mSelected.clear();
            }
        }
        
        public Iterator<Integer> getSelectedIterator(){
            return mSelected.iterator();
        }
        
        public boolean isSelected(int index){
            return mSelected.contains(index);
        }
    }
}
