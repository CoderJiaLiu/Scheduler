package com.lucas.scheduler;

import android.app.LoaderManager;

import com.lucas.scheduler.PresetParamsLoader.OnParamsLoadedListener;

public class PresetParamsLoadDirector implements OnParamsLoadedListener {
    private LoaderManager mManager;
    private PresetParamsLoader mLoader;
    private PresetParamsLoadFinishedListener mPresetParamsLoadFinishedListener;
    private boolean mFinished = false;
    public PresetParamsLoadDirector(PresetParamsLoader loader,LoaderManager manager){
        this(loader, manager, null);
    }
    
    public PresetParamsLoadDirector(PresetParamsLoader loader,LoaderManager manager,PresetParamsLoadFinishedListener l){
        mLoader = loader;
        mManager = manager;
        loader.setParamsLoaderListener(this);
        mPresetParamsLoadFinishedListener = l;
    }
    @Override
    public void onParamLoaded(int newLoaded) {
        // TODO Auto-generated method stub
        switch(newLoaded){
        case PresetParamsLoader.INTENTS_LOADED:
            mLoader.loadKinds(mManager);
            break;
        case PresetParamsLoader.KINDS_LOADED:
            mLoader.loadCategorys(mManager);
            break;
        case PresetParamsLoader.CATEGORYS_LOADED:
            mFinished = true;
            if(mPresetParamsLoadFinishedListener != null){
                mPresetParamsLoadFinishedListener.onLoadFinished();
            }
            break;
        }
    }

    public boolean isFinished(){
        return mFinished;
    }
    
    public void startLoad(){
        clear();
        mLoader.loadIntents(mManager);
    }
    
    public void clear(){
        mLoader.categorys.clear();
        mLoader.kinds.clear();
        mLoader.intents.clear();
    }
    
    public void setPresetParamsLoadFinishedListener(PresetParamsLoadFinishedListener l){
        mPresetParamsLoadFinishedListener = l;
    }
    
    public PresetParamsLoadFinishedListener getPresetParamsLoadFinishedListener(){
        return mPresetParamsLoadFinishedListener;
    }
    
    public interface PresetParamsLoadFinishedListener{
        void onLoadFinished();
    }
}
