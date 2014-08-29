package com.lucas.scheduler.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.lucas.scheduler.R;
import com.lucas.scheduler.utilities.Log;

public class ScheduleDatabaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "ScheduleDatabase.db";
	private static final int DB_VERSION = 1;
	private Context mContext;

	public enum TableEnum{
	    STUFF,
	    TASK,
	    ARRANGEMENT,
	    WORKFLOW,
	    INTENTS,
	    CATEGORY,
	    KIND,
	    REPEAT_RULE,
	    ATTENTION
	}
	
	public interface Tables {
		public static final String STUFF = "stuff";
		public static final String TASK = "task";
		public static final String ARRANGEMENT = "arrangement";
		public static final String WORKFLOW = "workflow";
		public static final String INTENTS = "intents";
		public static final String CATEGORY = "category";
		public static final String KIND = "kind";
		public static final String REPEAT_RULE = "repeat_rule";
		public static final String ATTENTION = "attentions";
	}

	public interface StuffColumns {
		public static final String ID = BaseColumns._ID;
		public static final String NAME = "name";
		public static final String STATUS = "status";
		public static final String DESCRIPTION = "description";
		public static final String IMPORTANCE = "importance";
		public static final String EMERGENCY = "emergency";
		public static final String CATEGORY = "category";
		public static final String KIND = "kind";
		public static final String DEADLINE = "deadline";
		public static final String INTENT = "intent";
		public static final String CREATION_TIME = "creation_time";
		public static final String PRESURE = "presure";
	}

	public interface StuffForeignKeys {
		public static final String CATEGORY_ID_FK = "category_id_fk";
		public static final String KIND_ID_FK = "kind_id_fk";
		public static final String INTENTS_ID_FK = "intents_id_fk";
	}

	public interface TaskColumns {
		public static final String ID = BaseColumns._ID;
		public static final String STUFF_ID = "stuff_id";
		public static final String CREATION_TIME = "creation_time";
		public static final String FOCUSABLE = "focusable";
	}

	public interface TaskForeignKeys {
		public static final String STUFF_ID_FK = "stuff_id_fk";
	}

	public interface ArrangementColumns {
		public static final String ID = BaseColumns._ID;
		public static final String TASK_ID = "task_id";
		public static final String TITLE = "title";
		public static final String PRIORITY = "priority";
		public static final String CANCEL_RESON = "cancel_reson";
		public static final String REPEAT_RULE = "repeat_rule";
		public static final String START_DATE = "start_date";
		public static final String END_DATE = "end_date";
		public static final String START_TIME = "start_time";
		public static final String END_TIME = "end_time";
	}

	public interface ArrangementForeignKeys {
		public static final String TASK_ID_FK = "task_id_fk";
	}

	public interface IntentsColumns {
		public static final String ID = BaseColumns._ID;
		public static final String DESCRIPTION = "description";
	}

	public interface CategoryColumns {
		public static final String ID = BaseColumns._ID;
		public static final String DESCRIPTION = "description";
		public static final String DEFAULT_INTENT_ID = "default_intent_id";
	}

	public interface CategoryForeignKeys {
		public static final String INTENT_ID_FK = "inent_id_fk";
	}

	public interface KindColumns {
		public static final String ID = BaseColumns._ID;
		public static final String CATEGORY_ID = "category_id";
		public static final String DESCRIPTION = "description";
		public static final String DEFAULT_INTENT_ID = "default_intent";
		public static final String DEFAULT_PRESURE = "default_presure";
	}

	public interface KindForeignKeys {
		public static final String CATEGORY_ID_FK = "category_id_fk";
		public static final String INTENT_ID_FK = "intent_id_fk";
	}

	public interface WorkflowColumns {
		public static final String ID = BaseColumns._ID;
		public static final String ARRANGEMENT_ID = "arrangement_id";
		public static final String TASK_ID = "task_id";
		public static final String TITLE = "title";
		public static final String START_DATETIME = "start_datetime";
		public static final String END_DATETIME = "end_datetime";
	}

	public interface WorkflowForeignKeys {
		public static final String ARRANGEMENT_ID_FK = "arragement_id_fk";
		public static final String TASK_ID_FK = "task_id_fk";
	}

	public interface RepeatColumns {
		public static final String ID = BaseColumns._ID;
		public static final String DESCRIPTION = "description";
	}

	public interface AttentionColumns {
		public static final String ID = BaseColumns._ID;
		public static final String TITLE = "title";
	}

	@Override
	public String getDatabaseName() {
		// TODO Auto-generated method stub
		return DB_NAME;
	}

	@Override
	public synchronized SQLiteDatabase getReadableDatabase() {
		// TODO Auto-generated method stub
		return super.getReadableDatabase();
	}

	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		// TODO Auto-generated method stub
		return super.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i(Log.FLAG_FRAMEWORK | Log.FLAG_DATA, "database helper on create");
		createTables(db);
		presetData(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		super.onDowngrade(db, oldVersion, newVersion);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	private void createTables(SQLiteDatabase db) {
		Log.dataOperation("create tables start");

		Log.dataOperation("Create " + Tables.INTENTS + " Begin");
		db.execSQL("CREATE TABLE " + Tables.INTENTS + " (" + IntentsColumns.ID
				+ " INTEGER PRIMARY KEY ," + IntentsColumns.DESCRIPTION
				+ " TEXT " + ");");
		Log.dataOperation("Create " + Tables.INTENTS + " Successfully");

		Log.dataOperation("Create " + Tables.CATEGORY + " Begin");
		db.execSQL("CREATE TABLE " + Tables.CATEGORY + " ("
				+ CategoryColumns.ID + " INTEGER PRIMARY KEY ,"
				+ CategoryColumns.DESCRIPTION + " TEXT ,"
				+ CategoryColumns.DEFAULT_INTENT_ID + " INTEGER ,"
				+ "CONSTRAINT " + CategoryForeignKeys.INTENT_ID_FK
				+ " FOREIGN KEY " + "(" + CategoryColumns.DEFAULT_INTENT_ID
				+ ")" + " REFERENCES " + Tables.INTENTS + " ( "
				+ IntentsColumns.ID + " )" + ");");
		Log.dataOperation("Create " + Tables.CATEGORY + " Successfully");

		Log.dataOperation("Create " + Tables.KIND + " Begin");
		db.execSQL("CREATE TABLE " + Tables.KIND + " (" + KindColumns.ID
				+ " INTEGER PRIMARY KEY ," + KindColumns.CATEGORY_ID
				+ " INTEGER , " + KindColumns.DESCRIPTION + " TEXT ,"
				+ KindColumns.DEFAULT_INTENT_ID + " INTEGER ,"
				+ KindColumns.DEFAULT_PRESURE + " INTEGER NOT NULL DEFAULT 0 "
				+ " CHECK " + " ( " + KindColumns.DEFAULT_PRESURE
				+ " >= 0 AND " + KindColumns.DEFAULT_PRESURE + " <= 10 "
				+ " ), " + "CONSTRAINT " + KindForeignKeys.CATEGORY_ID_FK
				+ " FOREIGN KEY " + "(" + KindColumns.CATEGORY_ID + ")"
				+ " REFERENCES " + Tables.CATEGORY + " ( " + IntentsColumns.ID
				+ " )" + "CONSTRAINT " + KindForeignKeys.INTENT_ID_FK
				+ " FOREIGN KEY " + "(" + KindColumns.DEFAULT_INTENT_ID + ")"
				+ " REFERENCES " + Tables.INTENTS + " ( " + IntentsColumns.ID
				+ " )" + ");");
		Log.dataOperation("Create " + Tables.CATEGORY + " Successfully");

		Log.dataOperation("Create " + Tables.STUFF + " Begin");
		db.execSQL("CREATE TABLE " + Tables.STUFF + " (" + StuffColumns.ID
				+ " INTEGER PRIMARY KEY ," + StuffColumns.NAME
				+ " TEXT NOT NULL," + StuffColumns.STATUS
				+ " INTEGER NOT NULL DEFAULT 0," + StuffColumns.IMPORTANCE
				+ " INTEGER NOT NULL DEFAULT 0 " + " CHECK " + " ( "
				+ StuffColumns.IMPORTANCE + " >= 0 AND "
				+ StuffColumns.IMPORTANCE + " <= 10 " + " ), "
				+ StuffColumns.EMERGENCY + " INTEGER NOT NULL DEFAULT 0 "
				+ " CHECK " + " ( " + StuffColumns.EMERGENCY + " >= 0 AND "
				+ StuffColumns.EMERGENCY + " <= 10 " + " ), "
				+ StuffColumns.PRESURE + " INTEGER NOT NULL DEFAULT 0 " + "CHECK " + " ( " + StuffColumns.PRESURE + " >= 0 AND " + StuffColumns.PRESURE + " <= 10 " + " ), " 
				+ StuffColumns.CATEGORY + " INTEGER NOT NULL DEFAULT 4 ," + StuffColumns.KIND
				+ " INTEGER NOT NULL DEFAULT 18 ," + StuffColumns.INTENT + " INTEGER,"
				+ StuffColumns.DESCRIPTION + " TEXT," + StuffColumns.DEADLINE
				+ " DATETIME," + StuffColumns.CREATION_TIME + " DATETIME" + " NOT NULL DEFAULT (datetime (CURRENT_TIMESTAMP,'localtime')) ,"
				+ "CONSTRAINT " + StuffForeignKeys.CATEGORY_ID_FK
				+ " FOREIGN KEY " + "(" + StuffColumns.CATEGORY + ")"
				+ " REFERENCES " + Tables.CATEGORY + " ( " + CategoryColumns.ID
				+ " ) ," + "CONSTRAINT " + StuffForeignKeys.KIND_ID_FK
				+ " FOREIGN KEY " + "(" + StuffColumns.KIND + ")"
				+ " REFERENCES " + Tables.KIND + " ( " + KindColumns.ID
				+ " ) ," + "CONSTRAINT " + StuffForeignKeys.INTENTS_ID_FK
				+ " FOREIGN KEY " + "(" + StuffColumns.INTENT + ")"
				+ " REFERENCES " + Tables.INTENTS + " ( " + IntentsColumns.ID
				+ " )" + ");");
		Log.dataOperation("Create " + Tables.STUFF + " Successfully");

		Log.dataOperation("Create " + Tables.TASK + " Begin");
		db.execSQL("CREATE TABLE " + Tables.TASK + " (" + TaskColumns.ID
				+ " INTEGER PRIMARY KEY ," + TaskColumns.STUFF_ID
				+ " INTEGER ," + TaskColumns.FOCUSABLE + " INTEGER "
				+ " CHECK " + " ( " + TaskColumns.FOCUSABLE + " IN "
				+ " (0,1) " + " )," + TaskColumns.CREATION_TIME + " DATETIME ,"
				+ "CONSTRAINT " + TaskForeignKeys.STUFF_ID_FK + " FOREIGN KEY "
				+ "(" + TaskColumns.STUFF_ID + ")" + " REFERENCES "
				+ Tables.STUFF + " ( " + StuffColumns.ID + " )" + ");");
		Log.dataOperation("Create " + Tables.STUFF + " Successfully");

		Log.dataOperation("Create " + Tables.REPEAT_RULE + " Begin");
		db.execSQL("CREATE TABLE " + Tables.REPEAT_RULE + " ("
				+ RepeatColumns.ID + " INTEGER PRIMARY KEY ,"
				+ RepeatColumns.DESCRIPTION + " TEXT" + ");");
		Log.dataOperation("Create " + Tables.REPEAT_RULE + " Successfully");

		Log.dataOperation("Create " + Tables.ATTENTION + " Begin");
		db.execSQL("CREATE TABLE " + Tables.ATTENTION + " ("
				+ AttentionColumns.ID + " INTEGER PRIMARY KEY ,"
				+ AttentionColumns.TITLE + " TEXT NOT NULL" + ");");
		Log.dataOperation("Create " + Tables.ATTENTION + " Successfully");

		Log.dataOperation("Create " + Tables.ARRANGEMENT + " Begin");
		db.execSQL("CREATE TABLE " + Tables.ARRANGEMENT + " ("
				+ ArrangementColumns.ID + " INTEGER PRIMARY KEY ,"
				+ ArrangementColumns.TASK_ID + " INTEGER ,"
				+ ArrangementColumns.REPEAT_RULE + " INTEGER ,"
				+ ArrangementColumns.TITLE + " TEXT NOT NULL ,"
				+ ArrangementColumns.PRIORITY + " INTEGER NOT NULL DEFAULT 0 "
				+ " CHECK " + " ( " + ArrangementColumns.PRIORITY
				+ " >= 0 AND " + ArrangementColumns.PRIORITY + " <= 10 "
				+ " ), " + ArrangementColumns.START_DATE + " DATE ,"
				+ ArrangementColumns.END_DATE + " DATE ,"
				+ ArrangementColumns.START_TIME + " TIME , "
				+ ArrangementColumns.END_TIME + " TIME , "
				+ ArrangementColumns.CANCEL_RESON + " TEXT , " + "CONSTRAINT "
				+ ArrangementForeignKeys.TASK_ID_FK + " FOREIGN KEY " + "("
				+ ArrangementColumns.TASK_ID + ")" + " REFERENCES "
				+ Tables.TASK + " ( " + TaskColumns.ID + " )" + ");");
		Log.dataOperation("Create " + Tables.ATTENTION + " Successfully");

		Log.dataOperation("Create " + Tables.WORKFLOW + " Begin");
		db.execSQL("CREATE TABLE " + Tables.WORKFLOW + " ("
				+ WorkflowColumns.ID + " INTEGER PRIMARY KEY ,"
				+ WorkflowColumns.ARRANGEMENT_ID + " INTEGER , "
				+ WorkflowColumns.TASK_ID + " INTEGER , "
				+ WorkflowColumns.TITLE + " TEXT ,"
				+ WorkflowColumns.START_DATETIME + " DATETIME , "
				+ WorkflowColumns.END_DATETIME + " DATETIME , " + "CONSTRAINT "
				+ WorkflowForeignKeys.ARRANGEMENT_ID_FK + " FOREIGN KEY " + "("
				+ WorkflowColumns.ARRANGEMENT_ID + ")" + " REFERENCES "
				+ Tables.ARRANGEMENT + " ( " + ArrangementColumns.ID + " )"
				+ "CONSTRAINT " + WorkflowForeignKeys.TASK_ID_FK
				+ " FOREIGN KEY " + "(" + WorkflowColumns.TASK_ID + ")"
				+ " REFERENCES " + Tables.TASK + " ( " + TaskColumns.ID + " )"
				+ ");");
		Log.dataOperation("Create " + Tables.ATTENTION + " Successfully");

		Log.dataOperation("all tables were created completelly");
	}

	private void presetData(SQLiteDatabase db) {
		Log.dataOperation("preset data START");
		Log.dataOperation("preset data get String Resources");
		String for_living = mContext.getString(R.string.intent_for_living);
		String for_better_living = mContext
				.getString(R.string.intent_for_better_living);
		String for_fun = mContext.getString(R.string.intent_for_fun);

		String category_work = mContext.getString(R.string.category_work);
		String category_study = mContext.getString(R.string.category_study);
		String category_xiuxian = mContext.getString(R.string.category_xiuxian);
		String category_amussement = mContext
				.getString(R.string.category_amussement);
		String category_shopping = mContext
				.getString(R.string.category_shopping);
		String category_daily = mContext.getString(R.string.category_daily);

		String kind_android = mContext.getString(R.string.kind_android);
		String kind_bathe = mContext.getString(R.string.kind_bathe);
		String kind_clean = mContext.getString(R.string.kind_clean);
		String kind_coding = mContext.getString(R.string.kind_coding);
		String kind_cooking = mContext.getString(R.string.kind_cooking);
		String kind_design = mContext.getString(R.string.kind_design);
		String kind_design_pattern = mContext
				.getString(R.string.kind_design_pattern);
		String kind_fix_bug = mContext.getString(R.string.kind_fix_bug);
		String kind_game = mContext.getString(R.string.kind_game);
		String kind_ktv = mContext.getString(R.string.kind_ktv);
		String kind_linux = mContext.getString(R.string.kind_linux);
		String kind_mai_cai = mContext.getString(R.string.kind_mai_cai);
		String kind_meeting = mContext.getString(R.string.kind_meeting);
		String kind_net_shopping = mContext
				.getString(R.string.kind_net_shopping);
		String kind_network = mContext.getString(R.string.kind_network);
		String kind_release = mContext.getString(R.string.kind_release);
		String kind_shower = mContext.getString(R.string.kind_shower);
		String kind_sleeping = mContext.getString(R.string.kind_sleeping);
		String kind_sql = mContext.getString(R.string.kind_sql);
		String kind_street = mContext.getString(R.string.kind_street);
		String kind_super_market = mContext
				.getString(R.string.kind_super_market);
		String kind_tour = mContext.getString(R.string.kind_tour);
		String kind_vacation = mContext.getString(R.string.kind_vacation);
		String kind_wash_dish = mContext.getString(R.string.kind_wash_dish);

		Log.dataOperation("preset data transaction begin");
		// db.beginTransaction();
		ContentValues values = new ContentValues();
		try {
			Log.dataOperation("preset data set intent begin");
			values.put(IntentsColumns.DESCRIPTION, for_living);
			db.insert(Tables.INTENTS, null, values);
			values.put(IntentsColumns.DESCRIPTION, for_better_living);
			db.insert(Tables.INTENTS, null, values);
			values.put(IntentsColumns.DESCRIPTION, for_fun);
			db.insert(Tables.INTENTS, null, values);
			values.clear();
			Log.dataOperation("preset data set intent successfully");

			Log.dataOperation("preset data set category begin");
			insertCategory(db, category_work, 1);
			insertCategory(db, category_study, for_better_living);
			insertCategory(db, category_shopping, for_better_living);
			insertCategory(db, category_daily, for_living);
			insertCategory(db, category_amussement, for_fun);
			insertCategory(db, category_xiuxian, for_better_living);
			Log.dataOperation("preset data set category successfully");

			Log.dataOperation("preset data set kind begin");
			insertKind(db, kind_android, 2, 2);
			insertKind(db, kind_bathe, category_xiuxian, for_fun, 5);
			insertKind(db, kind_clean, category_daily, for_living, 4);
			insertKind(db, kind_coding, category_work, for_living, 7);
			insertKind(db, kind_cooking, category_daily, for_living, 5);
			insertKind(db, kind_design, category_work, for_living, 8);
			insertKind(db, kind_design_pattern, category_study, for_fun, 7);
			insertKind(db, kind_fix_bug, category_work, for_living, 6);
			insertKind(db, kind_game, category_amussement, for_fun, 7);
			insertKind(db, kind_ktv, category_amussement, for_fun, 4);
			insertKind(db, kind_linux, category_study, for_better_living, 7);
			insertKind(db, kind_mai_cai, category_daily, for_living, 3);
			insertKind(db, kind_meeting, category_work, for_living, 3);
			insertKind(db, kind_net_shopping, category_shopping, for_living, 3);
			insertKind(db, kind_network, category_study, for_better_living, 6);
			insertKind(db, kind_release, category_work, for_living, 9);
			insertKind(db, kind_shower, category_daily, for_living, 2);
			insertKind(db, kind_sleeping, category_daily, for_living, 0);
			insertKind(db, kind_sql, category_study, for_better_living, 6);
			insertKind(db, kind_street, category_shopping, for_living, 3);
			insertKind(db, kind_super_market, category_shopping, for_living, 3);
			insertKind(db, kind_tour, category_xiuxian, for_fun, 5);
			insertKind(db, kind_vacation, 6, 3, 2);
			insertKind(db, kind_wash_dish, category_daily, 2);
			Log.dataOperation("preset data set kind successfully");

		} finally {
			// db.endTransaction();
		}
		// Log.dataOperation("preset data set category begin");
		// values.put(CategoryColumns., for_living);
		// values.put(IntentsColumns.DESCRIPTION, for_better_living);
		// values.put(IntentsColumns.DESCRIPTION, for_fun);
		// db.insert(Tables.INTENTS, null, values);
		// Log.dataOperation("preset data set category successfully");

		Log.dataOperation("preset data END");
	}

	static public void insertCategory(SQLiteDatabase db, String description,
			String default_intent) {
		db.execSQL("INSERT INTO " + Tables.CATEGORY + " ("
				+ CategoryColumns.DESCRIPTION + ","
				+ CategoryColumns.DEFAULT_INTENT_ID + ") " + " VALUES " + " ("
				+ "\'" + description + "\'" + "," + " (" + " SELECT "
				+ IntentsColumns.ID + " FROM " + Tables.INTENTS + " WHERE "
				+ Tables.INTENTS + "." + IntentsColumns.DESCRIPTION + " = "
				+ "\'" + default_intent + "\'" + ") " + ")");
	}

	static public void insertCategory(SQLiteDatabase db, String description,
			int default_intent_id) {
		db.execSQL("INSERT INTO " + Tables.CATEGORY + " ("
				+ CategoryColumns.DESCRIPTION + ","
				+ CategoryColumns.DEFAULT_INTENT_ID + ") " + " VALUES " + " ("
				+ "\'" + description + "\'" + "," + default_intent_id + ")");
	}

	static public void insertKind(SQLiteDatabase db, String description,
			String category, String default_intent, int default_pressure) {
		db.execSQL("INSERT INTO " + Tables.KIND + " ("
				+ KindColumns.DESCRIPTION + "," + KindColumns.CATEGORY_ID + ","
				+ KindColumns.DEFAULT_INTENT_ID + ","
				+ KindColumns.DEFAULT_PRESURE + ") " + " VALUES " + " (" + "\'"
				+ description + "\'" + "," + " (" + " SELECT "
				+ CategoryColumns.ID + " FROM " + Tables.CATEGORY + " WHERE "
				+ Tables.CATEGORY + "." + CategoryColumns.DESCRIPTION + " = "
				+ "\'" + category + "\'" + ") " + "," + " (" + " SELECT "
				+ IntentsColumns.ID + " FROM " + Tables.INTENTS + " WHERE "
				+ Tables.INTENTS + "." + IntentsColumns.DESCRIPTION + " = "
				+ "\'" + default_intent + "\'" + ") " + "," + default_pressure
				+ ")");
	}

	static public void insertKind(SQLiteDatabase db, String description,
			String category, int default_pressure) {
		db.execSQL("INSERT INTO " + Tables.KIND + " ("
				+ KindColumns.DESCRIPTION + "," + KindColumns.CATEGORY_ID + ","
				+ KindColumns.DEFAULT_INTENT_ID + ","
				+ KindColumns.DEFAULT_PRESURE + ") " + " VALUES " + " (" + "\'"
				+ description + "\'" + "," + " (" + " SELECT "
				+ CategoryColumns.ID + " FROM " + Tables.CATEGORY + " WHERE "
				+ Tables.CATEGORY + "." + CategoryColumns.DESCRIPTION + " = "
				+ "\'" + category + "\'" + ") " + "," + " (" + " SELECT "
				+ CategoryColumns.DEFAULT_INTENT_ID + " FROM "
				+ Tables.CATEGORY + " WHERE " + Tables.CATEGORY + "."
				+ CategoryColumns.DESCRIPTION + " = " + "\'" + category + "\'"
				+ " ) ," + default_pressure + ")");
	}

	static public void insertKind(SQLiteDatabase db, String description,
			int category_id, int default_pressure) {
		db.execSQL("INSERT INTO " + Tables.KIND + " ("
				+ KindColumns.DESCRIPTION + "," + KindColumns.CATEGORY_ID + ","
				+ KindColumns.DEFAULT_INTENT_ID + ","
				+ KindColumns.DEFAULT_PRESURE + ") " + " VALUES " + " (" + "\'"
				+ description + "\'" + "," + category_id + "," + "("
				+ " SELECT " + CategoryColumns.DEFAULT_INTENT_ID + " FROM "
				+ Tables.CATEGORY + " WHERE " + Tables.CATEGORY + "."
				+ CategoryColumns.ID + " = " + category_id + ")" + ","
				+ default_pressure + ")");
	}

	static public void insertKind(SQLiteDatabase db, String description,
			int category_id, int default_intent_id, int default_pressure) {
		db.execSQL("INSERT INTO " + Tables.KIND + " ("
				+ KindColumns.DESCRIPTION + "," + KindColumns.CATEGORY_ID + ","
				+ KindColumns.DEFAULT_INTENT_ID + ","
				+ KindColumns.DEFAULT_PRESURE + ") " + " VALUES " + " (" + "\'"
				+ description + "\'" + "," + category_id + ","
				+ default_intent_id + "," + default_pressure + ")");
	}

	static public void insertStuff(SQLiteDatabase db, String name){
		Log.transation("SchedulerModel insertStuff");
		db.execSQL(" INSERT INTO " + Tables.STUFF + " (" + StuffColumns.NAME + "," + StuffColumns.CREATION_TIME + ") VALUES (" + "\'" + name + "\' ," + "DATETIME(\"NOW\"))" );
	}
	public ScheduleDatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
		mContext = context;
	}
}
