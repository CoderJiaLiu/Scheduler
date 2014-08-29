package com.lucas.scheduler;

import java.util.ArrayList;

import android.app.LoaderManager;
import android.content.Context;
import android.database.Cursor;

import com.lucas.scheduler.data.Category;
import com.lucas.scheduler.data.Intent;
import com.lucas.scheduler.data.Kind;
import com.lucas.scheduler.data.Param;
import com.lucas.scheduler.data.ScheduleDatabaseHelper.CategoryColumns;
import com.lucas.scheduler.data.ScheduleDatabaseHelper.IntentsColumns;
import com.lucas.scheduler.data.ScheduleDatabaseHelper.KindColumns;
import com.lucas.scheduler.utilities.CursorLoaderUtil;

public class PresetParamsLoader {
    public int loaded = 0;
    public static final int INTENTS_LOADED = 1;
    public static final int KINDS_LOADED = 1 << 1;
    public static final int CATEGORYS_LOADED = 1 << 2;
    public static final int LOADED_MASK = INTENTS_LOADED | KINDS_LOADED
            | CATEGORYS_LOADED;
    public ArrayList<Param> intents = new ArrayList<Param>();
    public ArrayList<Param> kinds = new ArrayList<Param>();
    public ArrayList<Param> categorys = new ArrayList<Param>();
    private SchedulerModel mModel;
    private OnParamsLoadedListener mParamsLoaderListener;
    
    public PresetParamsLoader(Context context,SchedulerModel model){
        mModel = model;
    }

    public void loadIntents(LoaderManager manager) {
        mModel.startLoad(manager, null, SchedulerModel.INTENTS_LOADER_ID);
    }

    public void loadKinds(LoaderManager manager) {
        mModel.startLoad(manager, null, SchedulerModel.KINDS_LOADER_ID);
    }

    public void loadCategorys(LoaderManager manager) {
        mModel.startLoad(manager, null, SchedulerModel.CATEGORYS_LOADER_ID);
    }
    
    public Intent generateIntentFromCursor(Cursor c){
        Intent intent = new Intent();
        intent.id = CursorLoaderUtil.getInt(c, IntentsColumns.ID);
        intent.title = CursorLoaderUtil.getString(c, IntentsColumns.DESCRIPTION);
        return intent;
    }
    
    public Kind generateKindFromCursor(Cursor c){
        Kind kind = new Kind();
        kind.id = CursorLoaderUtil.getInt(c, KindColumns.ID);
        kind.title = CursorLoaderUtil.getString(c, KindColumns.DESCRIPTION);
        kind.defaultIntentId = CursorLoaderUtil.getInt(c, KindColumns.DEFAULT_INTENT_ID);
        kind.categoryId = CursorLoaderUtil.getInt(c, KindColumns.CATEGORY_ID);
        kind.defaultPresure = CursorLoaderUtil.getInt(c, KindColumns.DEFAULT_PRESURE);
        return kind;
    }
    
    public Category generateCategoryFromCursor(Cursor c){
        Category category = new Category();
        category.id = CursorLoaderUtil.getInt(c,CategoryColumns.ID);
        category.defaultIntentId = CursorLoaderUtil.getInt(c,CategoryColumns.DEFAULT_INTENT_ID);
        category.title = CursorLoaderUtil.getString(c, CategoryColumns.DESCRIPTION);
        return category;
    }
    public void onParamLoaded(int newLoaded,Cursor c){
        c.moveToFirst();
        switch(newLoaded){
        case INTENTS_LOADED:
            do{
                intents.add(generateIntentFromCursor(c));
            }while(c.moveToNext());
            break;
        case KINDS_LOADED:
            do{
                kinds.add(generateKindFromCursor(c));
            }while(c.moveToNext());
            break;
        case CATEGORYS_LOADED:
            do{
                Category category = generateCategoryFromCursor(c);
                for(Param param: kinds){
                    Kind kind = (Kind)param;
                    if(kind.categoryId == category.id && !category.kinds.contains(kind)){
                        category.kinds.add(kind);
                    }
                }
                categorys.add(category);
            }while(c.moveToNext());
            break;
        }
        if(mParamsLoaderListener != null){
            mParamsLoaderListener.onParamLoaded(newLoaded);
        }
    }
    public interface OnParamsLoadedListener{
        public void onParamLoaded(int newLoaded);
    }
    public void setParamsLoaderListener(OnParamsLoadedListener l){
        mParamsLoaderListener = l;
    }
}