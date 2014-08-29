package com.lucas.scheduler.data;

public abstract class AbstractParam implements Param {

    @Override
    public char charAt(int index) {
        // TODO Auto-generated method stub
        return getTitle().charAt(index);
    }

    @Override
    public int length() {
        // TODO Auto-generated method stub
        return getTitle().length();
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        // TODO Auto-generated method stub
        return getTitle().subSequence(start, end);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return getTitle();
    }
    
    
}
