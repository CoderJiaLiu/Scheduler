package com.lucas.scheduler.ui.widget.mutiselection;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.FrameLayout;

public class MultiSelectItemView extends FrameLayout {
    private View mOrig;
    private CheckBox mCB;
    public MultiSelectItemView(Context context) {
        this(context, (AttributeSet)null);
        // TODO Auto-generated constructor stub
    }
    
    public MultiSelectItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public MultiSelectItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public MultiSelectItemView(Context context,View orig) {
        this(context);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setLayoutParams(lp);
        addView(orig);
        mOrig = orig;
        mCB = new CheckBox(context);
        LayoutParams cblp = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        cblp.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        mCB.setGravity(Gravity.CENTER);
        mCB.setLayoutParams(cblp);
        mCB.setClickable(false);
        addView(mCB);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        // TODO Auto-generated constructor stub
    }
    
    public View getOrigView(){
        return mOrig;
    }
    
    public void setOrigView(View v){
        removeView(mOrig);
        addView(v, 0);
        mOrig = v;
    }
    
    public CheckBox getCheckBox(){
        return mCB;
    }
}
