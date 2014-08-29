package com.lucas.scheduler;

import android.app.LoaderManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

import com.lucas.scheduler.PresetParamsLoadDirector.PresetParamsLoadFinishedListener;

public class PresetParamsChangeObserver extends ContentObserver {
    private LoaderManager mLoaderManager;
    private PresetParamsLoadFinishedListener mPresetParamsLoadFinishedListener;
    private SchedulerModel mModel;
    public PresetParamsChangeObserver(Handler handler,SchedulerModel model,LoaderManager manager,PresetParamsLoadFinishedListener l) {
        super(handler);
        // TODO Auto-generated constructor stub
        mModel = model;
        mLoaderManager = manager;
        mPresetParamsLoadFinishedListener = l;
    }
    
    @Override
    public void onChange(boolean selfChange, Uri uri) {
        // TODO Auto-generated method stub
        super.onChange(selfChange, uri);
    }

    @Override
    public void onChange(boolean selfChange) {
        // TODO Auto-generated method stub
        super.onChange(selfChange);
        PresetParamsLoadDirector dirctor = new PresetParamsLoadDirector(mModel.getPresetParamsLoader(),mLoaderManager,mPresetParamsLoadFinishedListener);
        dirctor.startLoad();
    }
    
}
