package com.lucas.scheduler.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.net.Uri.Builder;

import com.lucas.scheduler.data.ScheduleDatabaseHelper.TableEnum;
import com.lucas.scheduler.data.ScheduleDatabaseHelper.Tables;
import com.lucas.scheduler.utilities.Log;

public class SchedulerProvider extends ContentProvider {
	public static final String AUTHORITY = "com.lucas.scheduler.provider";
	
	private static final int STUFF = 0;
	private static final int STUFF_ID = 1;
	private static final int INTENT = 2;
	private static final int INTENT_ID = 3;
	private static final int ATTENTION = 4;
	private static final int ATTENTION_ID = 5;
	private static final int KIND = 6;
	private static final int KIND_ID = 7;
	private static final int CATEGORY = 8;
	private static final int CATEGORY_ID = 9;
	private static final int ARRANGEMENT = 10;
	private static final int ARRANGEMENT_ID = 11;
	private static final int TASK = 12;
	private static final int TASK_ID = 13;
	private static final int WORKFLOW = 14;
	private static final int WORKFLOW_ID = 15;
	private static UriMatcher sMatcher = new LogUriMatcher(UriMatcher.NO_MATCH);
	private ScheduleDatabaseHelper mHelper;
	private SQLiteDatabase mDatabase;
	static{
	    Log.i(Log.FLAG_DATA | Log.FLAG_FRAMEWORK, "SchedulerProvider on class load");
	    sMatcher.addURI(AUTHORITY, "stuff", STUFF);
	    sMatcher.addURI(AUTHORITY, "stuff/#", STUFF_ID);
	    sMatcher.addURI(AUTHORITY, "intent", INTENT);
	    sMatcher.addURI(AUTHORITY, "intent/#", INTENT_ID);
	    sMatcher.addURI(AUTHORITY, "attention", ATTENTION);
	    sMatcher.addURI(AUTHORITY, "attention/#", ATTENTION_ID);
	    sMatcher.addURI(AUTHORITY, "kind", KIND);
	    sMatcher.addURI(AUTHORITY, "kind/#", KIND_ID);
	    sMatcher.addURI(AUTHORITY, "category", CATEGORY);
	    sMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ID);
	    sMatcher.addURI(AUTHORITY, "task", TASK);
        sMatcher.addURI(AUTHORITY, "task/#", TASK_ID);
        sMatcher.addURI(AUTHORITY, "arrangement", ARRANGEMENT);
        sMatcher.addURI(AUTHORITY, "arrangement/#", ARRANGEMENT_ID);
        sMatcher.addURI(AUTHORITY, "workflow", WORKFLOW);
        sMatcher.addURI(AUTHORITY, "workflow/#", WORKFLOW_ID);
	}
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
	    Log.i(Log.FLAG_DATA | Log.FLAG_FRAMEWORK, "SchedulerProvider onCreate");
	    mHelper = new ScheduleDatabaseHelper(getContext());
	    mDatabase = mHelper.getWritableDatabase();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
	    Log.dataOperation("SchedulerProvider query uri = " + uri);
	    if(mDatabase == null){
	        Log.w(Log.FLAG_DATA, "database is null return null");
	        return null;
	    }
	    if(uri == null){
	        Log.w(Log.FLAG_DATA, "uri is null return null");
	        return null;
	    }
	    int code = sMatcher.match(uri);
	    Cursor c = null;
	    switch (code) {
        case STUFF:
            c = mDatabase.query(ScheduleDatabaseHelper.Tables.STUFF, projection, selection, selectionArgs, null, null, sortOrder);
            break;
        case STUFF_ID:
            c = mDatabase.query(ScheduleDatabaseHelper.Tables.STUFF, projection, ScheduleDatabaseHelper.StuffColumns.ID + " = " + uri.getLastPathSegment(), selectionArgs, null, null, sortOrder);
            break;
        case ATTENTION:
            c = mDatabase.query(ScheduleDatabaseHelper.Tables.ATTENTION, projection, selection, selectionArgs, null, null, sortOrder);
            break;
        case ATTENTION_ID:
            c = mDatabase.query(ScheduleDatabaseHelper.Tables.ATTENTION, projection, ScheduleDatabaseHelper.AttentionColumns.ID + " = " + uri.getLastPathSegment(), selectionArgs, null, null, sortOrder);
            break;
        case INTENT:
            c = mDatabase.query(ScheduleDatabaseHelper.Tables.INTENTS, projection, selection, selectionArgs, null, null, sortOrder);
            break;
        case INTENT_ID:
            c = mDatabase.query(ScheduleDatabaseHelper.Tables.INTENTS, projection, ScheduleDatabaseHelper.IntentsColumns.ID + " = " + uri.getLastPathSegment(), selectionArgs, null, null, sortOrder);
            break;
        case CATEGORY:
            c = mDatabase.query(ScheduleDatabaseHelper.Tables.CATEGORY, projection, selection, selectionArgs, null, null, sortOrder);
            break;
        case CATEGORY_ID:
            c = mDatabase.query(ScheduleDatabaseHelper.Tables.CATEGORY, projection, ScheduleDatabaseHelper.CategoryColumns.ID + " = " + uri.getLastPathSegment(), selectionArgs, null, null, sortOrder);
            break;
        case KIND:
            c = mDatabase.query(ScheduleDatabaseHelper.Tables.KIND, projection, selection, selectionArgs, null, null, sortOrder);
            break;
        case KIND_ID:
            c = mDatabase.query(ScheduleDatabaseHelper.Tables.KIND, projection, ScheduleDatabaseHelper.KindColumns.ID + " = " + uri.getLastPathSegment(), selectionArgs, null, null, sortOrder);
            break;
        default:
            Log.w(Log.FLAG_DATA, "uri match nothing query failed");
            break;
        }
		return c;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
	    if(uri == null){
            Log.w(Log.FLAG_DATA, "uri is null return null");
            return null;
        }
        int code = sMatcher.match(uri);
        String result = null;
        switch (code) {
        case STUFF:
            result = "vnd.android.cursor.dir/stuff";
            break;
        case STUFF_ID:
            result = "vnd.android.cursor.item/stuff";
            break;
        case ATTENTION:
            result = "vnd.android.cursor.dir/attention";            
            break;
        case ATTENTION_ID:
            result = "vnd.android.cursor.item/attention";  
            break;
        case INTENT:
            result = "vnd.android.cursor.dir/intent"; 
            break;
        case INTENT_ID:
            result = "vnd.android.cursor.item/intent";
            break;
        case CATEGORY:
            result = "vnd.android.cursor.dir/category"; 
            break;
        case CATEGORY_ID:
            result = "vnd.android.cursor.item/category"; 
            break;
        case KIND:
            result = "vnd.android.cursor.dir/kind"; 
            break;
        case KIND_ID:
            result = "vnd.android.cursor.item/kind"; 
            break;
        default:
            Log.w(Log.FLAG_DATA, "uri match nothing cant get type");
            break;
        }
		return result;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
	    Log.dataOperation("SchedulerProvider insert uri = " + uri);
        if(mDatabase == null){
            Log.w(Log.FLAG_DATA, "database is null return null");
            return null;
        }
        if(uri == null){
            Log.w(Log.FLAG_DATA, "uri is null return null");
            return null;
        }
        int code = sMatcher.match(uri);
        long id = -1;
	    switch (code) {
        case STUFF:
            id = mDatabase.insert(ScheduleDatabaseHelper.Tables.STUFF, null, values);
            break;
        case ATTENTION:
            id = mDatabase.insert(ScheduleDatabaseHelper.Tables.ATTENTION, null, values);
            break;
        case INTENT:
            id = mDatabase.insert(ScheduleDatabaseHelper.Tables.INTENTS, null, values);
            break;
        case CATEGORY:
            id = mDatabase.insert(ScheduleDatabaseHelper.Tables.CATEGORY, null, values);
            break;
        case KIND:
            id = mDatabase.insert(ScheduleDatabaseHelper.Tables.KIND, null, values);
            break;
        default:
            Log.w(Log.FLAG_DATA, "uri match nothing cant insert");
            break;
        }
	    getContext().getContentResolver().notifyChange(uri, null);
	    return ContentUris.withAppendedId(uri, id);
    }

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
	    Log.dataOperation("SchedulerProvider query uri = " + uri);
        if(mDatabase == null){
            Log.w(Log.FLAG_DATA, "database is null return -1");
            return -1;
        }
        if(uri == null){
            Log.w(Log.FLAG_DATA, "uri is null return -1");
            return -1;
        }
        int code = sMatcher.match(uri);
        int id = -1;
        switch (code) {
        case STUFF:
            id = mDatabase.delete(ScheduleDatabaseHelper.Tables.STUFF, selection, selectionArgs);
            break;
        case STUFF_ID:
//            id = mDatabase.delete(ScheduleDatabaseHelper.Tables.STUFF, selection + " AND " + ScheduleDatabaseHelper.StuffColumns.ID + " = " + uri.getLastPathSegment(), selectionArgs);
            id = mDatabase.delete(ScheduleDatabaseHelper.Tables.STUFF, ScheduleDatabaseHelper.StuffColumns.ID + " = " + uri.getLastPathSegment(), selectionArgs);
            break;
        case ATTENTION:
            id = mDatabase.delete(ScheduleDatabaseHelper.Tables.ATTENTION, selection, selectionArgs);
            break;
        case ATTENTION_ID:
            id = mDatabase.delete(ScheduleDatabaseHelper.Tables.ATTENTION, ScheduleDatabaseHelper.AttentionColumns.ID + " = " + uri.getLastPathSegment(), selectionArgs);
            break;
        case INTENT:
            id = mDatabase.delete(ScheduleDatabaseHelper.Tables.INTENTS, selection, selectionArgs);
            break;
        case INTENT_ID:
            id = mDatabase.delete(ScheduleDatabaseHelper.Tables.INTENTS, ScheduleDatabaseHelper.IntentsColumns.ID + " = " + uri.getLastPathSegment(), selectionArgs);
            break;
        case CATEGORY:
            id = mDatabase.delete(ScheduleDatabaseHelper.Tables.CATEGORY, selection, selectionArgs);
            break;
        case CATEGORY_ID:
            id = mDatabase.delete(ScheduleDatabaseHelper.Tables.CATEGORY, ScheduleDatabaseHelper.CategoryColumns.ID + " = " + uri.getLastPathSegment(), selectionArgs);
            break;
        case KIND:
            id = mDatabase.delete(ScheduleDatabaseHelper.Tables.KIND, selection, selectionArgs);
            break;
        case KIND_ID:
            id = mDatabase.delete(ScheduleDatabaseHelper.Tables.KIND, ScheduleDatabaseHelper.KindColumns.ID + " = " + uri.getLastPathSegment(), selectionArgs);
            break;
        default:
            Log.w(Log.FLAG_DATA, "uri match nothing cant insert");
            break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
		return id;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
	    Log.dataOperation("SchedulerProvider query uri = " + uri);
        if(mDatabase == null){
            Log.w(Log.FLAG_DATA, "database is null return -1");
            return -1;
        }
        if(uri == null){
            Log.w(Log.FLAG_DATA, "uri is null return -1");
            return -1;
        }
        int code = sMatcher.match(uri);
        int id = -1;
        switch (code) {
        case STUFF:
            id = mDatabase.update(ScheduleDatabaseHelper.Tables.STUFF,values, selection, selectionArgs);
            break;
        case STUFF_ID:
            id = mDatabase.update(ScheduleDatabaseHelper.Tables.STUFF,values, ScheduleDatabaseHelper.StuffColumns.ID + " = " + uri.getLastPathSegment(), selectionArgs);
            break;
        case ATTENTION:
            id = mDatabase.update(ScheduleDatabaseHelper.Tables.ATTENTION,values, selection, selectionArgs);
            break;
        case ATTENTION_ID:
            id = mDatabase.update(ScheduleDatabaseHelper.Tables.ATTENTION,values, ScheduleDatabaseHelper.AttentionColumns.ID + " = " + uri.getLastPathSegment(), selectionArgs);
            break;
        case INTENT:
            id = mDatabase.update(ScheduleDatabaseHelper.Tables.INTENTS,values, selection, selectionArgs);
            break;
        case INTENT_ID:
            id = mDatabase.update(ScheduleDatabaseHelper.Tables.INTENTS,values, ScheduleDatabaseHelper.IntentsColumns.ID + " = " + uri.getLastPathSegment(), selectionArgs);
            break;
        case CATEGORY:
            id = mDatabase.update(ScheduleDatabaseHelper.Tables.CATEGORY,values, selection, selectionArgs);
            break;
        case CATEGORY_ID:
            id = mDatabase.update(ScheduleDatabaseHelper.Tables.CATEGORY,values,ScheduleDatabaseHelper.CategoryColumns.ID + " = " + uri.getLastPathSegment(), selectionArgs);
            break;
        case KIND:
            id = mDatabase.update(ScheduleDatabaseHelper.Tables.KIND,values, selection, selectionArgs);
            break;
        case KIND_ID:
            id = mDatabase.update(ScheduleDatabaseHelper.Tables.KIND,values,ScheduleDatabaseHelper.KindColumns.ID + " = " + uri.getLastPathSegment(), selectionArgs);
            break;
        default:
            Log.w(Log.FLAG_DATA, "uri match nothing cant insert");
            break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return id;
	}
	private static class LogUriMatcher extends UriMatcher{

        public LogUriMatcher(int code) {
            super(code);
            // TODO Auto-generated constructor stub
        }

        @Override
        public int match(Uri uri) {
            // TODO Auto-generated method stub
            int code =  super.match(uri);
            switch(code){
            case STUFF:
                Log.dataOperation("uri match to stuff");
                break;
            case STUFF_ID:
                Log.dataOperation("uri match to stuff id");
                break;
            case INTENT:
                Log.dataOperation("uri match to intent");
                break;
            case INTENT_ID:
                Log.dataOperation("uri match to intent id");
                break;
            case ATTENTION:
                Log.dataOperation("uri match to attention");
                break;
            case ATTENTION_ID:
                Log.dataOperation("uri match to attention id");
                break;
            case KIND:
                Log.dataOperation("uri match to kind");
                break;
            case KIND_ID:
                Log.dataOperation("uri match to kind id");
                break;
            case CATEGORY:
                Log.dataOperation("uri match to category");
                break;
            case CATEGORY_ID:
                Log.dataOperation("uri match to category id");
                break;
            case ARRANGEMENT:
                Log.dataOperation("uri match to arrangement");
                break;
            case ARRANGEMENT_ID:
                Log.dataOperation("uri match to arrangement id");
                break;
            case TASK:
                Log.dataOperation("uri match to task");
                break;
            case TASK_ID:
                Log.dataOperation("uri match to task id");
                break;
            case WORKFLOW:
                Log.dataOperation("uri match to workflow");
                break;
            case WORKFLOW_ID:
                Log.dataOperation("uri match to workflow id");
                break;
            default:
                Log.dataOperation("uri match to nothing");
                break;
            }
            return code;
        }
	    
	}
	
	public static Uri generateUri(TableEnum table){
	    Builder builder = new Builder();
	    builder.scheme("content");
	    builder.authority(AUTHORITY);
	    
	    switch(table){
	    case STUFF:
	        builder.path(Tables.STUFF);
	        break;
	    case TASK:
	        builder.path(Tables.TASK);
            break;
	    case WORKFLOW:
	        builder.path(Tables.WORKFLOW);
            break;
	    case ARRANGEMENT:
	        builder.path(Tables.ARRANGEMENT);
            break;
	    case ATTENTION:
	        builder.path(Tables.ATTENTION);
            break;
	    case CATEGORY:
	        builder.path(Tables.CATEGORY);
            break;
	    case INTENTS:
	        builder.path(Tables.INTENTS);
            break;
	    case KIND:
	        builder.path(Tables.KIND);
            break;
	    case REPEAT_RULE:
	        builder.path(Tables.REPEAT_RULE);
            break;
	    default:
	        Log.e(Log.FLAG_DATA, "invalid arguments");
	        return null;
	    }
	    return builder.build();
	}
	
	public static Uri generateUri(TableEnum table,int id){
	    Uri uri = ContentUris.withAppendedId(generateUri(table), id);
	    return uri;
	}
}
