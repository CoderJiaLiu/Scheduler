package com.lucas.scheduler;

import java.util.ArrayList;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.lucas.scheduler.data.Param;
import com.lucas.scheduler.data.ScheduleDatabaseHelper;
import com.lucas.scheduler.data.ScheduleDatabaseHelper.CategoryColumns;
import com.lucas.scheduler.data.ScheduleDatabaseHelper.KindColumns;
import com.lucas.scheduler.data.ScheduleDatabaseHelper.TableEnum;
import com.lucas.scheduler.data.SchedulerProvider;
import com.lucas.scheduler.data.Stuff;
import com.lucas.scheduler.utilities.Log;

public class SchedulerModel implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String KEY_STUFF_ID = "stuff_id";
    public static final String KEY_STUFF_URI = "stuff_uri";
    private Context mContext;
    private OnLoadCompliteListener mCompliteListener;
    private Handler mHandler = new Handler();
    public final static int STUFF_LIST_LOADER_ID = 0;
    public final static int STUFF_DETAIL_LOADER_ID = 1;
    public final static int INTENTS_LOADER_ID = 2;
    public final static int KINDS_LOADER_ID = 3;
    public final static int CATEGORYS_LOADER_ID = 4;
    public static final String[] STUFF_LIST_QUERY_PROJECTION = {
            ScheduleDatabaseHelper.StuffColumns.ID,
            ScheduleDatabaseHelper.StuffColumns.NAME,
            ScheduleDatabaseHelper.StuffColumns.CREATION_TIME,
            ScheduleDatabaseHelper.StuffColumns.IMPORTANCE,
            ScheduleDatabaseHelper.StuffColumns.EMERGENCY,
            ScheduleDatabaseHelper.StuffColumns.PRESURE};
    public static final String[] INTENT_QUERY_PROJECTION = {
            ScheduleDatabaseHelper.IntentsColumns.ID,
            ScheduleDatabaseHelper.IntentsColumns.DESCRIPTION };
    public static final String[] KIND_QUERY_PROJECTION = {
            ScheduleDatabaseHelper.KindColumns.ID,
            ScheduleDatabaseHelper.KindColumns.DESCRIPTION,
            ScheduleDatabaseHelper.KindColumns.CATEGORY_ID,
            ScheduleDatabaseHelper.KindColumns.DEFAULT_INTENT_ID,
            ScheduleDatabaseHelper.KindColumns.DEFAULT_PRESURE };
    public static final String[] CATEGORY_QUERY_PROJECTION = {
            ScheduleDatabaseHelper.CategoryColumns.ID,
            ScheduleDatabaseHelper.CategoryColumns.DEFAULT_INTENT_ID,
            ScheduleDatabaseHelper.CategoryColumns.DESCRIPTION };
    public static final String[] STUFF_DETAIL_QUERY_PROJECTION = {
            ScheduleDatabaseHelper.StuffColumns.ID,
            ScheduleDatabaseHelper.StuffColumns.NAME,
            ScheduleDatabaseHelper.StuffColumns.DESCRIPTION,
            ScheduleDatabaseHelper.StuffColumns.CREATION_TIME,
            ScheduleDatabaseHelper.StuffColumns.DEADLINE,
            ScheduleDatabaseHelper.StuffColumns.CATEGORY,
            ScheduleDatabaseHelper.StuffColumns.KIND,
            ScheduleDatabaseHelper.StuffColumns.INTENT,
            ScheduleDatabaseHelper.StuffColumns.IMPORTANCE,
            ScheduleDatabaseHelper.StuffColumns.EMERGENCY,
            ScheduleDatabaseHelper.StuffColumns.PRESURE,
            ScheduleDatabaseHelper.StuffColumns.STATUS };
    public static final Uri STUFF_LIST_URI = Uri.parse("content://"
            + SchedulerProvider.AUTHORITY + "/stuff");
    public static final Uri INTENT_LIST_URI = Uri.parse("content://"
            + SchedulerProvider.AUTHORITY + "/intent");
    public static final Uri KIND_LIST_URI = Uri.parse("content://"
            + SchedulerProvider.AUTHORITY + "/kind");
    public static final Uri CATEGORY_LIST_URI = Uri.parse("content://"
            + SchedulerProvider.AUTHORITY + "/category");
    private PresetParamsLoader mParamsLoader;

    private SchedulerModel(Context context) {
        Log.framwork("create SchedulerModel");
        mContext = context;
        mParamsLoader = new PresetParamsLoader(mContext, this);
    }
    private static SchedulerModel sSchedulerModel;
    public static SchedulerModel getInstance(Context context) {
        Log.framwork("get SchedulerModel get Instance");
        if (sSchedulerModel == null) {
            sSchedulerModel = new SchedulerModel(context);
        }
        return sSchedulerModel;
    }

    public void startLoad(LoaderManager manager, Bundle args, int id) {
        Log.transation("get SchedulerModel start load id = " + id);
        manager.restartLoader(id, args, this);
    }

    public interface OnLoadCompliteListener {
        public void onLoadComplite(Cursor c, int id);

        public void onLoaderReset(Loader<Cursor> loader);
    }

    public void setOnLoadCompliteListener(OnLoadCompliteListener listener) {
        Log.transation("SchedulerModel setOnStuffQueryCompliteListener "
                + listener.hashCode());
        mCompliteListener = listener;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // TODO Auto-generated method stub
        Loader<Cursor> loader = null;
        switch (id) {
        case STUFF_LIST_LOADER_ID:
            Log.transation("create stuff loader");
            loader = new CursorLoader(mContext, STUFF_LIST_URI,
                    STUFF_LIST_QUERY_PROJECTION, null, null, null);
            break;
        case STUFF_DETAIL_LOADER_ID:
            Uri stuffUri = Uri.parse(args.getString(KEY_STUFF_URI));
            loader = new CursorLoader(mContext, stuffUri,
                    STUFF_DETAIL_QUERY_PROJECTION, null, null, null);
            break;
        case INTENTS_LOADER_ID:
            loader = new CursorLoader(mContext, INTENT_LIST_URI,
                    INTENT_QUERY_PROJECTION, null, null, null);
            break;
        case KINDS_LOADER_ID:
            loader = new CursorLoader(mContext, KIND_LIST_URI,
                    KIND_QUERY_PROJECTION, null, null, null);
            break;
        case CATEGORYS_LOADER_ID:
            loader = new CursorLoader(mContext, CATEGORY_LIST_URI,
                    CATEGORY_QUERY_PROJECTION, null, null, null);
            break;
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // TODO Auto-generated method stub
        Log.transation("get SchedulerModel onLoadFinished id = "
                + loader.getId());
        switch (loader.getId()) {
        case INTENTS_LOADER_ID:
            mParamsLoader
                    .onParamLoaded(PresetParamsLoader.INTENTS_LOADED, data);
            if (data != null) {
                data.close();
            }
            return;
        case KINDS_LOADER_ID:
            mParamsLoader.onParamLoaded(PresetParamsLoader.KINDS_LOADED, data);
            if (data != null) {
                data.close();
            }
            return;
        case CATEGORYS_LOADER_ID:
            mParamsLoader.onParamLoaded(PresetParamsLoader.CATEGORYS_LOADED,
                    data);
            if (data != null) {
                data.close();
            }
            return;
        default:
            break;
        }
        if (mCompliteListener != null) {
            mCompliteListener.onLoadComplite(data, loader.getId());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub
        if (loader.getId() == STUFF_LIST_LOADER_ID && mCompliteListener != null) {
            mCompliteListener.onLoaderReset(loader);
        }
        loader.abandon();
    }

    public Uri insertStuff(final String name) {
        Log.transation("get SchedulerModel insertStuff name = " + name);
        if (name == null) {
            Log.w(Log.FLAG_TRANSATION,
                    "get SchedulerModel insertStuff name = null return null");
            return null;
        }
        ContentValues values = new ContentValues();
        values.put(ScheduleDatabaseHelper.StuffColumns.NAME, name);
        return mContext.getContentResolver().insert(STUFF_LIST_URI, values);
    }

    public Uri insertStuff(final ContentValues values) {
        Log.transation("get SchedulerModel insertStuff");
        return mContext.getContentResolver().insert(STUFF_LIST_URI, values);
    }

    public Handler getHandler() {
        return mHandler;
    }

    public void deleteStuff(final int id) {
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                mContext.getContentResolver().delete(SchedulerProvider.generateUri(TableEnum.STUFF, id), null, null);
            }
        }).start();
    }

    public ArrayList<Param> getIntents() {
        return mParamsLoader.intents;
    }

    public ArrayList<Param> getKinds() {
        return mParamsLoader.kinds;
    }

    public ArrayList<Param> getCategorys() {
        return mParamsLoader.categorys;
    }

    public PresetParamsLoader getPresetParamsLoader() {
        return mParamsLoader;
    }
    
    public void updateStuff(final Stuff stuff,final ContentValues values){
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                mContext.getContentResolver().update(stuff.getUri(), values, null, null);
            }
        }).start();
    }
    
    public Uri addCategory(String title,int defaultIntentId){
        ContentValues values = new ContentValues();
        values.put(CategoryColumns.DESCRIPTION, title);
        values.put(CategoryColumns.DEFAULT_INTENT_ID, defaultIntentId);
        return mContext.getContentResolver().insert(SchedulerProvider.generateUri(TableEnum.CATEGORY), values);
    }
    
    public Uri addKind(String title,int categoryId,int defaultIntentId){
        ContentValues values = new ContentValues();
        values.put(KindColumns.DESCRIPTION, title);
        values.put(KindColumns.DEFAULT_INTENT_ID, defaultIntentId);
        values.put(KindColumns.CATEGORY_ID, categoryId);
        return mContext.getContentResolver().insert(SchedulerProvider.generateUri(TableEnum.KIND), values);
    }
    
    public void deleteCategory(final int id){
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                mContext.getContentResolver().delete(SchedulerProvider.generateUri(TableEnum.CATEGORY, id), null, null);
            }
        }).start();
    }
    
    public void deleteKind(final int id){
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                mContext.getContentResolver().delete(SchedulerProvider.generateUri(TableEnum.KIND, id), null, null);
            }
        }).start();
    }
}
