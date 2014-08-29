package com.lucas.scheduler.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.lucas.scheduler.R;
import com.lucas.scheduler.data.ScheduleDatabaseHelper;
import com.lucas.scheduler.utilities.ColorHelper;

public class StuffAdapter extends CursorAdapter {
    private LayoutInflater mInflater;
    private static final int MAX_PARAM = 10;
    
    @SuppressWarnings("deprecation")
    public StuffAdapter(Context context, Cursor c) {
        super(context, c);
        // TODO Auto-generated constructor stub
        mInflater = LayoutInflater.from(context);
    }
    
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // TODO Auto-generated method stub
      TextView tv = (TextView)view.findViewById(R.id.stuff_list_item_textview);
      TextView createTime = (TextView)view.findViewById(R.id.stuff_list_item_time);
      String name = cursor.getString(cursor.getColumnIndex(ScheduleDatabaseHelper.StuffColumns.NAME));
      String createTimeStr = cursor.getString(cursor.getColumnIndex(ScheduleDatabaseHelper.StuffColumns.CREATION_TIME));
      int pressure = cursor.getInt(cursor.getColumnIndex(ScheduleDatabaseHelper.StuffColumns.PRESURE));
      int importance = cursor.getInt(cursor.getColumnIndex(ScheduleDatabaseHelper.StuffColumns.IMPORTANCE));
      int emergency = cursor.getInt(cursor.getColumnIndex(ScheduleDatabaseHelper.StuffColumns.EMERGENCY));
      
      int color = ColorHelper.getNtoMColor(pressure, importance, emergency, MAX_PARAM);
      int inverseColor = ColorHelper.getInverseColor(color);
      ColorStateList csl = new ColorStateList(new int[][] {
                new int[] { -android.R.attr.state_pressed },
                new int[] { android.R.attr.state_pressed } }, new int[] {
                inverseColor, color });
      ColorDrawable colorDrawable = new ColorDrawable(color);
      ColorDrawable inverseDrawable = new ColorDrawable(inverseColor);
      StateListDrawable bg = new StateListDrawable();
      bg.addState(new int[]{-android.R.attr.state_pressed}, colorDrawable);
      bg.addState(new int[]{android.R.attr.state_pressed}, inverseDrawable);
      view.setBackground(bg);
      createTime.setText(createTimeStr);
      tv.setText(name);
      tv.setTextColor(csl);
      
      createTime.setTextColor(csl);
    }

    @Override
    public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        return mInflater.inflate(R.layout.stuff_list_item, null);
    }
}
