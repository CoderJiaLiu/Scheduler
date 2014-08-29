package com.lucas.scheduler.ui;

import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.lucas.scheduler.PresetParamsChangeObserver;
import com.lucas.scheduler.PresetParamsLoadDirector.PresetParamsLoadFinishedListener;
import com.lucas.scheduler.R;
import com.lucas.scheduler.SchedulerModel;
import com.lucas.scheduler.data.Param;
import com.lucas.scheduler.data.ScheduleDatabaseHelper.TableEnum;
import com.lucas.scheduler.data.SchedulerProvider;
import com.lucas.scheduler.ui.widget.mutiselection.MultiSelectAble.Mode;
import com.lucas.scheduler.ui.widget.mutiselection.MultiSelectionListView;

public class CategoryListActivity extends Activity implements OnClickListener,
        PresetParamsLoadFinishedListener ,OnItemClickListener{
    private MultiSelectionListView  mListView;
    private SchedulerModel mModel;
    private AlertDialog mAddCategoryDialog;
    private EditText mAddCategoryET;
    private Spinner mAddCategorySpinner;
    private ArrayAdapter<Param> mAdapter;
    private ContentObserver mObserver;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mModel = SchedulerModel.getInstance(this);
        setContentView(R.layout.multi_select_list_activity);
        setupViews();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mObserver = new PresetParamsChangeObserver(mModel.getHandler(), mModel, getLoaderManager(), this);
        getContentResolver().registerContentObserver(SchedulerProvider.generateUri(TableEnum.CATEGORY), true, mObserver);
    }
    
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        getContentResolver().unregisterContentObserver(mObserver);
    }

    private void setupViews(){
        mListView = (MultiSelectionListView)findViewById(R.id.list);
        mAdapter = new ArrayAdapter<Param>(this, R.layout.simple_textview_item ,mModel.getCategorys());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.category_list_menu, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if(id == R.id.add_category){
            if(mAddCategoryDialog == null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = LayoutInflater.from(this);
                View view = inflater.inflate(R.layout.dialog_edit_text_spinner, null);
                mAddCategoryET = (EditText) view.findViewById(R.id.edit_text);
                mAddCategoryET.setSingleLine(true);
                mAddCategorySpinner = (Spinner) view.findViewById(R.id.spinner);
                mAddCategorySpinner.setAdapter(new ParamsSpinnerAdapter(mModel.getIntents(), this));
                mAddCategoryDialog = builder.setTitle(R.string.input_des)
                        .setView(view)
                        .setNegativeButton(R.string.dialog_btn_cancel, this)
                        .setPositiveButton(R.string.dialog_btn_ok, this).create();
                mAddCategoryDialog.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            mAddCategoryET.setText("");
            mAddCategoryDialog.show();
        }else if(id == R.id.remove_categorys){
            onSelectionModeChanged(Mode.MULTISELECT);
        }else if(id == R.id.cancel){
            onSelectionModeChanged(Mode.NORMAL);
        }else if(id == R.id.ok){
            Iterator<Integer> iterator = mListView.getSelectedIterator();
            while(iterator.hasNext()){
                mModel.deleteCategory((int)((Param)mAdapter.getItem(iterator.next())).getId());
            }
            onSelectionModeChanged(Mode.NORMAL);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface arg0, int arg1) {
        // TODO Auto-generated method stub
        if(arg1 == DialogInterface.BUTTON_POSITIVE){
            int defaultIntentId = 1;
            defaultIntentId = (int)mAddCategorySpinner.getSelectedItemId();
            mModel.addCategory(mAddCategoryET.getText().toString(),defaultIntentId);
            arg0.dismiss();
        }else if(arg1 == DialogInterface.BUTTON_NEGATIVE){
        }
    }

    @Override
    public void onLoadFinished() {
        // TODO Auto-generated method stub
        mAdapter.notifyDataSetInvalidated();
    }
    private void onSelectionModeChanged(Mode mode){
        if(mode == Mode.MULTISELECT){
            mMenu.findItem(R.id.ok).setVisible(true);
            mMenu.findItem(R.id.cancel).setVisible(true);
            mMenu.findItem(R.id.add_category).setVisible(false);
            mMenu.findItem(R.id.remove_categorys).setVisible(false);
        }else{
            mMenu.findItem(R.id.ok).setVisible(false);
            mMenu.findItem(R.id.cancel).setVisible(false);
            mMenu.findItem(R.id.add_category).setVisible(true);
            mMenu.findItem(R.id.remove_categorys).setVisible(true);
        }
        mListView.setMutiSelectionMode(mode);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        // TODO Auto-generated method stub
        android.content.Intent intent = new Intent(android.content.Intent.ACTION_EDIT);
        intent.setData(SchedulerProvider.generateUri(TableEnum.CATEGORY, mAdapter.getItem(position).getId()));
        startActivity(intent);
    }
}