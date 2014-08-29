package com.lucas.scheduler.ui;

import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.lucas.scheduler.PresetParamsChangeObserver;
import com.lucas.scheduler.PresetParamsLoadDirector.PresetParamsLoadFinishedListener;
import com.lucas.scheduler.R;
import com.lucas.scheduler.SchedulerModel;
import com.lucas.scheduler.data.Category;
import com.lucas.scheduler.data.Param;
import com.lucas.scheduler.data.ScheduleDatabaseHelper.TableEnum;
import com.lucas.scheduler.data.SchedulerProvider;
import com.lucas.scheduler.ui.widget.mutiselection.MultiSelectAble.Mode;
import com.lucas.scheduler.ui.widget.mutiselection.MultiSelectionListView;

public class KindListActivity extends Activity implements PresetParamsLoadFinishedListener,OnClickListener{
    private Category mCategory;
    private SchedulerModel mModel;
    private ArrayAdapter<Param> mAdapter;
    private AlertDialog mAddKindDialog;
    private EditText mAddKindET;
    private Spinner mAddKindSpinner;
    private MultiSelectionListView mListView;
    private PresetParamsChangeObserver mObserver;
    private Menu mMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_select_list_activity);
        setupViews();
        mModel = SchedulerModel.getInstance(this);
        android.content.Intent intent = getIntent();
        initCategoryFromIntent(intent);
        mAdapter = new ArrayAdapter<Param>(this, R.layout.simple_textview_item, mCategory.kinds);
        mListView.setAdapter(mAdapter);
        
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mObserver = new PresetParamsChangeObserver(mModel.getHandler(), mModel, getLoaderManager(), this);
        getContentResolver().registerContentObserver(SchedulerProvider.generateUri(TableEnum.KIND), true, mObserver);
    }
    
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        getContentResolver().unregisterContentObserver(mObserver);
    }

    private void initCategoryFromIntent(android.content.Intent intent){
        if(intent != null){
            int id = Integer.parseInt(intent.getData().getLastPathSegment());
            reInitCategoryById(id);
        }
    }
    
    private void reInitCategoryById(int id){
        Iterator<Param> iterator = mModel.getCategorys().iterator();
        while(iterator.hasNext()){
            
            Param category = iterator.next();
            if(category.getId() == id){
                mCategory = (Category)category;
                return;
            }
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.kind_list_menu, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if(id == R.id.add_kind){
            if(mAddKindDialog == null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = LayoutInflater.from(this);
                View view = inflater.inflate(R.layout.dialog_edit_text_spinner, null);
                mAddKindET = (EditText) view.findViewById(R.id.edit_text);
                mAddKindET.setSingleLine(true);
                mAddKindSpinner = (Spinner) view.findViewById(R.id.spinner);
                mAddKindSpinner.setAdapter(new ParamsSpinnerAdapter(mModel.getIntents(), this));
                mAddKindDialog = builder.setTitle(R.string.input_des)
                        .setView(view)
                        .setNegativeButton(R.string.dialog_btn_cancel, this)
                        .setPositiveButton(R.string.dialog_btn_ok, this).create();
                mAddKindDialog.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            mAddKindDialog.show();
        }else if(id == R.id.remove_kinds){
            onSelectionModeChanged(Mode.MULTISELECT);
        }else if(id == R.id.cancel){
            onSelectionModeChanged(Mode.NORMAL);
        }else if(id == R.id.ok){
            Iterator<Integer> iterator = mListView.getSelectedIterator();
            while(iterator.hasNext()){
                mModel.deleteKind((mAdapter.getItem(iterator.next()).getId()));
            }
            onSelectionModeChanged(Mode.NORMAL);
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void onSelectionModeChanged(Mode mode){
        if(mode == Mode.MULTISELECT){
            mMenu.findItem(R.id.ok).setVisible(true);
            mMenu.findItem(R.id.cancel).setVisible(true);
            mMenu.findItem(R.id.add_kind).setVisible(false);
            mMenu.findItem(R.id.remove_kinds).setVisible(false);
        }else{
            mMenu.findItem(R.id.ok).setVisible(false);
            mMenu.findItem(R.id.cancel).setVisible(false);
            mMenu.findItem(R.id.add_kind).setVisible(true);
            mMenu.findItem(R.id.remove_kinds).setVisible(true);
        }
        mListView.setMutiSelectionMode(mode);
    }
    
    private void setupViews(){
        mListView = (MultiSelectionListView)findViewById(R.id.list);
    }

    @Override
    public void onLoadFinished() {
        // TODO Auto-generated method stub
        reInitCategoryById(mCategory.id);
        mAdapter.clear();
        mAdapter.addAll(mCategory.kinds);
        mAdapter.notifyDataSetInvalidated();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        // TODO Auto-generated method stub
        if(which == DialogInterface.BUTTON_POSITIVE){
            int defaultIntentId = 1;
            defaultIntentId = (int)mAddKindSpinner.getSelectedItemId();
            mModel.addKind(mAddKindET.getText().toString(),mCategory.getId(),defaultIntentId);
            dialog.dismiss();
        }else if(which == DialogInterface.BUTTON_NEGATIVE){
        }
    }

}
