package com.lucas.scheduler.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lucas.scheduler.OnStuffLoadedListener;
import com.lucas.scheduler.R;
import com.lucas.scheduler.SchedulerModel;
import com.lucas.scheduler.SchedulerModel.OnLoadCompliteListener;
import com.lucas.scheduler.StuffEditTransacation;
import com.lucas.scheduler.data.Category;
import com.lucas.scheduler.data.Intent;
import com.lucas.scheduler.data.Kind;
import com.lucas.scheduler.data.Param;
import com.lucas.scheduler.data.ScheduleDatabaseHelper.StuffColumns;
import com.lucas.scheduler.data.Stuff;
import com.lucas.scheduler.utilities.Log;

public class StuffEditActivity extends Activity implements
        OnLoadCompliteListener, OnItemSelectedListener, StuffEditTransacation,
        OnStuffLoadedListener, OnRatingBarChangeListener, OnClickListener,
        android.view.View.OnClickListener ,OnDateSetListener,OnTimeSetListener{
    private static final int DES_EDIT_TEXT_MAX_LINES = 7;
    private Uri mStuffUri;
    private SchedulerModel mModel;
    private Stuff mStuff;
    private ArrayList<Param> mIntents;
    private ArrayList<Param> mKinds;
    private ArrayList<Param> mCategorys;

    private Spinner mCategorySpinner;
    private Spinner mKindSpinner;
    private Spinner mIntentSpinner;

    private TextView mPressuerLabel;
    private RatingBar mPressureRatingBar;

    private TextView mImportanceLabel;
    private RatingBar mImportanceRatingBar;

    private TextView mEmergencyLabel;
    private RatingBar mEmergencyRatingBar;

    private TextView mDescriptionTV;

    private AlertDialog mDescriptionDialog;
    private EditText mDescriptionDialogET;

    private ParamsSpinnerAdapter mKindAdapter;
    private View mDeadDateLayout;
    private View mDeadTimeLayout;
    private TextView mDeadTimeLabel;
    private TextView mDeadTimeDes;

    private TextView mDeadDateLabel;
    private TextView mDeadDateDes;
    
    private DatePickerDialog mDeadDatePickerDialog;
    private TimePickerDialog mDeadTimePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.framwork("StuffEditActivity onCreate");
        setContentView(R.layout.stuff_edit_activity);
        mModel = SchedulerModel.getInstance(this);
        mIntents = mModel.getIntents();
        mKinds = mModel.getKinds();
        mCategorys = mModel.getCategorys();
        mStuffUri = getIntent().getData();
        setupViews();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.ui("StuffEditActivity onResume");
        Bundle extra = new Bundle();
        extra.putString(SchedulerModel.KEY_STUFF_URI, mStuffUri.toString());
        mModel.setOnLoadCompliteListener(this);
        mModel.startLoad(getLoaderManager(), extra,
                SchedulerModel.STUFF_DETAIL_LOADER_ID);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    public void onLoadComplite(Cursor c, int id) {
        // TODO Auto-generated method stub
        Log.transation("StuffEditActivity onLoadComplite");
        if (id == SchedulerModel.STUFF_DETAIL_LOADER_ID) {
            c.moveToFirst();
            mStuff = Stuff.getInstanceFromCursor(c);
            onStuffLoadedUpdateUI(mStuff);
        }
        if (c != null) {
            c.close();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub
        mStuff = null;
    }

    private void setupViews() {
        mCategorySpinner = (Spinner) findViewById(R.id.category_sp);
        mKindSpinner = (Spinner) findViewById(R.id.kind_sp);
        mIntentSpinner = (Spinner) findViewById(R.id.intent_sp);
        mCategorySpinner.setAdapter(new ParamsSpinnerAdapter(mCategorys, this));
        mIntentSpinner.setAdapter(new ParamsSpinnerAdapter(mIntents, this));
        mKindAdapter = new ParamsSpinnerAdapter(mKinds, this);
        mKindSpinner.setAdapter(mKindAdapter);
        View v = findViewById(R.id.pressure_rb_area);
        mPressuerLabel = (TextView) v.findViewById(R.id.label);
        mPressureRatingBar = (RatingBar) v.findViewById(R.id.rating);
        mPressuerLabel.setText(R.string.pressure);
        mPressureRatingBar.setOnRatingBarChangeListener(this);
        v = findViewById(R.id.importance_rb_area);
        mImportanceLabel = (TextView) v.findViewById(R.id.label);
        mImportanceRatingBar = (RatingBar) v.findViewById(R.id.rating);
        mImportanceRatingBar.setOnRatingBarChangeListener(this);
        mImportanceLabel.setText(R.string.importance);
        v = findViewById(R.id.emergency_rb_area);
        mEmergencyLabel = (TextView) v.findViewById(R.id.label);
        mEmergencyRatingBar = (RatingBar) v.findViewById(R.id.rating);
        mEmergencyRatingBar.setOnRatingBarChangeListener(this);
        mEmergencyLabel.setText(R.string.emergency);
        mDescriptionTV = (TextView) findViewById(R.id.description);
        mDescriptionTV.setClickable(true);
        mDescriptionTV.setOnClickListener(this);
        mDeadDateLayout = findViewById(R.id.dead_date_area);
        mDeadDateLabel = (TextView) mDeadDateLayout.findViewById(R.id.label);
        mDeadDateDes = (TextView) mDeadDateLayout.findViewById(R.id.des);
        mDeadDateLayout.setOnClickListener(this);
        mDeadDateLabel.setText(R.string.dead_date);
        mDeadTimeLayout = findViewById(R.id.dead_time_area);
        mDeadTimeLabel = (TextView) mDeadTimeLayout.findViewById(R.id.label);
        mDeadTimeDes = (TextView) mDeadTimeLayout.findViewById(R.id.des);
        mDeadTimeLayout.setOnClickListener(this);
        mDeadTimeLabel.setText(R.string.dead_time);
    }

    private Param getParam(ArrayList<Param> params, int id) {
        if (params == null) {
            return null;
        }
        for (Param param : params) {
            if (param.getId() == id) {
                return param;
            }
        }
        return null;
    }

    private int getPosition(ArrayList<Param> params, int id) {
        int pos = -1;
        if (params == null) {
            return pos;
        }
        for (pos = 0; pos < params.size(); pos++) {
            if (params.get(pos).getId() == id) {
                return pos;
            }
        }
        return -1;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
            long id) {
        // TODO Auto-generated method stub
        Log.transation("StuffEditActivity onItemSelected");
        if (parent == mCategorySpinner && id != mStuff.getCategory()) {
            onCategoryChanged((int) id);
        } else if (parent == mKindSpinner && id != mStuff.getKind()) {
            onKindChanged((int) id);
        } else if (parent == mIntentSpinner && id != mStuff.getIntent()) {
            onIntentChanged((int) id);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }
    
    @Override
    public void onCategoryChanged(int categoryId) {
        // TODO Auto-generated method stub
        Log.transation("StuffEditActivity onCategoryChanged = " + categoryId);
        ContentValues values = new ContentValues();
        Category category = (Category) getParam(mCategorys, (int) categoryId);
        mKinds = category.kinds;
        mKindAdapter.swapData(category.kinds);
        mStuff.setCategory(category.id);
        values.put(StuffColumns.CATEGORY, category.id);
        mModel.updateStuff(mStuff, values);
    }

    @Override
    public void onKindChanged(int kindId) {
        // TODO Auto-generated method stub
        Log.transation("StuffEditActivity onKindChanged = " + kindId);
        ContentValues values = new ContentValues();
        Kind kind = (Kind) getParam(mKinds, (int) kindId);
        int pos = getPosition(mIntents, kind.defaultIntentId);
        if (mIntentSpinner != null && pos < mIntentSpinner.getCount()) {
            mIntentSpinner.setSelection(pos);
        }
        mStuff.setKind(kind.id);
        mPressureRatingBar.setRating(convertToRating(kind.defaultPresure));
        values.put(StuffColumns.KIND, kind.id);
        mModel.updateStuff(mStuff, values);
    }

    @Override
    public void onIntentChanged(int intentId) {
        // TODO Auto-generated method stub
        Log.transation("StuffEditActivity onIntentChanged = " + intentId);
        ContentValues values = new ContentValues();
        Intent intent = (Intent) getParam(mIntents, (int) intentId);
        mStuff.setIntent(intent.id);
        values.put(StuffColumns.INTENT, intent.id);
        mModel.updateStuff(mStuff, values);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStuffLoadedUpdateUI(Stuff stuff) {
        // TODO Auto-generated method stub
        Log.i(Log.FLAG_TRANSATION | Log.FLAG_UI,
                "StuffEditActivity onStuffLoadedUpdateUI");
        getActionBar().setTitle(stuff.getName());
        Log.i(Log.FLAG_TRANSATION | Log.FLAG_UI,
                "StuffEditActivity update category selection android kind data");
        int pos = getPosition(mCategorys, stuff.getCategory());
        Category category = (Category) getParam(mCategorys, stuff.getCategory());
        mKinds = category.kinds;
        if (mKindAdapter == null) {
            mKindAdapter = new ParamsSpinnerAdapter(category.kinds, this);
        } else {
            mKindAdapter.swapData(mKinds);
        }
        mCategorySpinner.setSelection(pos > 0 ? pos : 0);

        Log.i(Log.FLAG_TRANSATION | Log.FLAG_UI,
                "StuffEditActivity update kind selection");
        pos = getPosition(mKinds, mStuff.getKind());
        mKindSpinner.setSelection(pos > 0 ? pos : 0);

        Log.i(Log.FLAG_TRANSATION | Log.FLAG_UI,
                "StuffEditActivity update intent selection");
        pos = getPosition(mIntents, mStuff.getIntent());
        mIntentSpinner.setSelection(pos > 0 ? pos : 0);

        Log.transation("StuffEditActivity set spinner OnItemSelected Listeners");
        mCategorySpinner.setOnItemSelectedListener(this);
        mKindSpinner.setOnItemSelectedListener(this);
        mIntentSpinner.setOnItemSelectedListener(this);

        mPressureRatingBar.setRating(convertToRating(stuff.getPresure()));
        mImportanceRatingBar.setRating(convertToRating(stuff.getImportance()));
        mEmergencyRatingBar.setRating(convertToRating(stuff.getEmergency()));

        mDescriptionTV.setText(stuff.getDescription());
        
        mDeadDateDes.setText(stuff.getDeadDate());
        mDeadTimeDes.setText(stuff.getDeadTime());
        Date date = stuff.getDeadLineDateObj();
        if(date == null){
            date = new Date(System.currentTimeMillis());
        }
        mDeadDatePickerDialog = new DatePickerDialog(this,this,date.getYear() + 1900, date.getMonth(), date.getDate());
        mDeadTimePickerDialog = new TimePickerDialog(this,this,date.getHours(), date.getMinutes(), true);
    }

    private float convertToRating(int param) {
        return (float) param / 2;
    }

    private int convertFromRating(float rating) {
        return (int) (rating * 2);
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating,
            boolean fromUser) {
        // TODO Auto-generated method stub
        if (ratingBar == mPressureRatingBar) {
            onPressureChanged(convertFromRating(rating));
        } else if (ratingBar == mImportanceRatingBar) {
            onImportanceChanged(convertFromRating(rating));
        } else if (ratingBar == mEmergencyRatingBar) {
            onEmergencyChanged(convertFromRating(rating));
        }
    }

    @Override
    public void onPressureChanged(int pressure) {
        // TODO Auto-generated method stub
        ContentValues values = new ContentValues();
        values.put(StuffColumns.PRESURE, pressure);
        mModel.updateStuff(mStuff, values);
    }

    @Override
    public void onImportanceChanged(int importance) {
        // TODO Auto-generated method stub
        ContentValues values = new ContentValues();
        values.put(StuffColumns.IMPORTANCE, importance);
        mModel.updateStuff(mStuff, values);
    }

    @Override
    public void onEmergencyChanged(int emergency) {
        // TODO Auto-generated method stub
        ContentValues values = new ContentValues();
        values.put(StuffColumns.EMERGENCY, emergency);
        mModel.updateStuff(mStuff, values);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(v == mDescriptionTV){
            String description = mDescriptionTV.getText().toString();
            if (mDescriptionDialog == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = LayoutInflater.from(this);
                View view = inflater.inflate(R.layout.dialog_edit_text, null);
                mDescriptionDialogET = (EditText) view.findViewById(R.id.edit_text);
                mDescriptionDialogET.setSingleLine(false);
                mDescriptionDialogET.setMaxLines(DES_EDIT_TEXT_MAX_LINES);
                mDescriptionDialog = builder.setTitle(R.string.input_des)
                        .setView(view)
                        .setNegativeButton(R.string.dialog_btn_cancel, this)
                        .setPositiveButton(R.string.dialog_btn_ok, this).create();
                mDescriptionDialog.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            if (description != null && !description.isEmpty()) {
                mDescriptionDialogET.setText(description);
                mDescriptionDialogET.setSelection(description.length());
            }
            mDescriptionDialog.show();
        }else if(v == mDeadDateLayout){
            mDeadDatePickerDialog.show();
        }else if(v == mDeadTimeLayout){
            mDeadTimePickerDialog.show();
        }
        
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        // TODO Auto-generated method stub
        if (which == DialogInterface.BUTTON_POSITIVE) {
            String des = mDescriptionDialogET.getText().toString();
            if (mDescriptionTV.getText() == null
                    || !mDescriptionTV.getText().toString().equals(des)) {
                mDescriptionTV.setText(des);
                onDescriptionChanged(des);
            }
            dialog.dismiss();
        } else {
            dialog.dismiss();
        }
    }

    @Override
    public void onDescriptionChanged(String des) {
        // TODO Auto-generated method stub
        ContentValues values = new ContentValues();
        values.put(StuffColumns.DESCRIPTION, des);
        mModel.updateStuff(mStuff, values);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // TODO Auto-generated method stub
        Date olddate = mStuff.getDeadLineDateObj();
        Date date = new Date(System.currentTimeMillis());
        if(olddate != null){
            date.setYear(olddate.getYear());
            date.setMonth(olddate.getMonth());
            date.setDate(olddate.getDate());
        }
        date.setHours(hourOfDay);
        date.setMinutes(minute);
        date.setSeconds(0);
        mStuff.setDeadLineDateObj(date);
        onDeadLineChanged(mStuff.getDeadLine());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        mDeadTimeDes.setText(sdf.format(date));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
            int dayOfMonth) {
        // TODO Auto-generated method stub
        Date olddate = mStuff.getDeadLineDateObj();
        Date date = new Date(System.currentTimeMillis());
        if(olddate != null){
            date.setHours(olddate.getHours());
            date.setMinutes(olddate.getMinutes());
            date.setSeconds(olddate.getSeconds());
        }
        date.setYear(year - 1900);
        date.setMonth(monthOfYear);
        date.setDate(dayOfMonth);
        mStuff.setDeadLineDateObj(date);
        onDeadLineChanged(mStuff.getDeadLine());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mDeadDateDes.setText(sdf.format(date));
    }

    @Override
    public void onDeadLineChanged(String deadline) {
        // TODO Auto-generated method stub
        ContentValues values = new ContentValues();
        values.put(StuffColumns.DEADLINE, deadline);
        mModel.updateStuff(mStuff, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.stuff_edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if(item.getItemId() == R.id.edit_categorys){
            android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.dir/category");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    
}
