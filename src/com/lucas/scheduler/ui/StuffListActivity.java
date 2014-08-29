package com.lucas.scheduler.ui;

import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.Loader;
import android.database.ContentObserver;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

import com.lucas.scheduler.PresetParamsLoadDirector;
import com.lucas.scheduler.R;
import com.lucas.scheduler.SchedulerModel;
import com.lucas.scheduler.SchedulerModel.OnLoadCompliteListener;
import com.lucas.scheduler.data.ScheduleDatabaseHelper.TableEnum;
import com.lucas.scheduler.data.SchedulerProvider;
import com.lucas.scheduler.ui.widget.mutiselection.MultiSelectAble.Mode;
import com.lucas.scheduler.ui.widget.mutiselection.MultiSelectionListView;
import com.lucas.scheduler.utilities.Log;

public class StuffListActivity extends Activity implements OnClickListener,OnLoadCompliteListener,OnItemClickListener{
	private SchedulerModel mModel;
	private AlertDialog mAddStuffDialog = null;
	private EditText mAddStuffDialogEditText;
	private MultiSelectionListView mListView;
	private StuffAdapter mAdapter;
	private ContentObserver mStuffObserver;
	private Menu mMenu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stuff_activity);
		mModel = SchedulerModel.getInstance(this);
		mModel.setOnLoadCompliteListener(this);
		mListView = (MultiSelectionListView)findViewById(R.id.stuff_list);
		mListView.setOnItemClickListener(this);
		PresetParamsLoadDirector  director = new PresetParamsLoadDirector(mModel.getPresetParamsLoader(), getLoaderManager());
		director.startLoad();
		mStuffObserver = new ContentObserver(mModel.getHandler()) {

	        @Override
	        public boolean deliverSelfNotifications() {
	            // TODO Auto-generated method stub
	            Log.i(Log.FLAG_DATA | Log.FLAG_UI, "stuff content observer deliverSelfNotifications");
	            return super.deliverSelfNotifications();
	        }

	        @Override
	        public void onChange(boolean selfChange) {
	            // TODO Auto-generated method stub
	            Log.i(Log.FLAG_DATA | Log.FLAG_UI, "stuff content observer onChange reload stuff");
	            mModel.startLoad(getLoaderManager(), null, SchedulerModel.STUFF_LIST_LOADER_ID);
	            super.onChange(selfChange);
	        }

	        @Override
	        public void onChange(boolean selfChange, Uri uri) {
	            // TODO Auto-generated method stub
	            Log.i(Log.FLAG_DATA | Log.FLAG_UI, "stuff content observer onChange reload stuff");
	            Log.i(Log.FLAG_DATA | Log.FLAG_UI, "stuff content observer onChange");
	            super.onChange(selfChange, uri);
	        }
	        
	    };
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mModel.setOnLoadCompliteListener(this);
		mModel.startLoad(getLoaderManager(), null, SchedulerModel.STUFF_LIST_LOADER_ID);
		getContentResolver().registerContentObserver(SchedulerModel.STUFF_LIST_URI, true, mStuffObserver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = new MenuInflater(this);
		inflater.inflate(R.menu.stuff_menu, menu);
		mMenu = menu;
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
	    int id = item.getItemId();
		if(id == R.id.add_stuff){
			if(mAddStuffDialog == null){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				LayoutInflater inflater = LayoutInflater.from(this);
				View v = inflater.inflate(R.layout.dialog_edit_text, null);
				mAddStuffDialogEditText = (EditText) v.findViewById(R.id.edit_text);
				mAddStuffDialogEditText.setHint(R.string.dialog_edittext_hint_stuff);
				mAddStuffDialogEditText.setSingleLine(true);
				mAddStuffDialog = builder.setTitle(R.string.dialog_title_create_stuff).setView(v)
						.setNegativeButton(R.string.dialog_btn_cancel, this)
						.setPositiveButton(R.string.dialog_btn_ok, this).create();
				mAddStuffDialog.getWindow().setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			}
			mAddStuffDialogEditText.setText(null);
			mAddStuffDialog.show();
		}else if(id == R.id.delete_stuffs){
		    onSelectionModeChanged(Mode.MULTISELECT);
		}else if(id == R.id.cancel){
		    onSelectionModeChanged(Mode.NORMAL);
		}else if(id == R.id.ok){
            Iterator<Integer> iterator = mListView.getSelectedIterator();
            while(iterator.hasNext()){
                mModel.deleteStuff((int)mAdapter.getItemId(iterator.next()));
            }
            onSelectionModeChanged(Mode.NORMAL);
        }
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		if(arg1 == AlertDialog.BUTTON_NEGATIVE){
			Log.ui("Dialog NEGATIVE btn onClick");
			arg0.dismiss();
		}else if(arg1 == AlertDialog.BUTTON_POSITIVE){
			Log.ui("Dialog POSITIVE btn onClick");
			mModel.insertStuff(mAddStuffDialogEditText.getText().toString());
		}
	}

	@Override
	public void onLoadComplite(final Cursor c, int id) {
		// TODO Auto-generated method stub
		Log.transation("StuffActivity onLoadComplite");
		if(mAdapter == null){
			Log.transation("first time onQueryComplite");
			mAdapter = new StuffAdapter(this,c);
			mListView.setAdapter(mAdapter);
		}else{
			Log.transation("change cursor");
            mAdapter.swapCursor(c);
		}
	}

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        getContentResolver().unregisterContentObserver(mStuffObserver);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setData(SchedulerProvider.generateUri(TableEnum.STUFF, (int)id));
        startActivity(intent);
    }
    
    private void onSelectionModeChanged(Mode mode){
        if(mode == Mode.MULTISELECT){
            mMenu.findItem(R.id.ok).setVisible(true);
            mMenu.findItem(R.id.cancel).setVisible(true);
            mMenu.findItem(R.id.add_stuff).setVisible(false);
            mMenu.findItem(R.id.delete_stuffs).setVisible(false);
        }else{
            mMenu.findItem(R.id.ok).setVisible(false);
            mMenu.findItem(R.id.cancel).setVisible(false);
            mMenu.findItem(R.id.add_stuff).setVisible(true);
            mMenu.findItem(R.id.delete_stuffs).setVisible(true);
        }
        mListView.setMutiSelectionMode(mode);
    }
}
