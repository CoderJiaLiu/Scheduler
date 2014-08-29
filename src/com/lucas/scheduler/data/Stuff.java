package com.lucas.scheduler.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.database.Cursor;
import android.net.Uri;

import com.lucas.scheduler.data.ScheduleDatabaseHelper.StuffColumns;
import com.lucas.scheduler.data.ScheduleDatabaseHelper.TableEnum;
import com.lucas.scheduler.utilities.CursorLoaderUtil;
import com.lucas.scheduler.utilities.Log;

public class Stuff {
    private int mId = -1;
    private int mIntent = -1;
    private int mKind = -1;
    private int mCategory = -1;
    private int mPresure = -1;
    private int mImportance = -1;
    private int mEmergency = -1;
    private String mName;
    private String mDescription;
    private String mCreationTime;
    private String mDeadLine;
    private int mStatus = -1;
    private Date mDeadLineDateObj;
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getIntent() {
        return mIntent;
    }

    public void setIntent(int intent) {
        this.mIntent = intent;
    }

    public int getKind() {
        return mKind;
    }

    public void setKind(int kind) {
        this.mKind = kind;
    }

    public int getCategory() {
        return mCategory;
    }

    public void setCategory(int category) {
        this.mCategory = category;
    }

    public int getPresure() {
        return mPresure;
    }

    public void setPresure(int presure) {
        this.mPresure = presure;
    }

    public int getImportance() {
        return mImportance;
    }

    public void setImportance(int importance) {
        this.mImportance = importance;
    }

    public int getEmergency() {
        return mEmergency;
    }

    public void setEmergency(int emergency) {
        this.mEmergency = emergency;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getCreationTime() {
        return mCreationTime;
    }

    public void setCreationTime(String creationTime) {
        this.mCreationTime = creationTime;
    }

    public String getDeadLine() {
        return mDeadLine;
    }

    public void setDeadLine(String deadLine) {
        this.mDeadLine = deadLine;
        if(mDeadLine == null){
            return;
        }
        try {
            mDeadLineDateObj = FORMAT.parse(deadLine);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public int getStatus(){
        return mStatus;
    }
    
    public void setStatus(int status){
        if(mStatus != status){
            mStatus = status;
        }
    }
    public static Stuff getInstanceFromCursor(Cursor c){
        if(c == null){
            Log.w(Log.FLAG_DATA, "cursor is null cant get stuff");
            return null;
        }
        Stuff stuff =  new Stuff();
        stuff.setId(CursorLoaderUtil.getInt(c, StuffColumns.ID));
        stuff.setIntent(CursorLoaderUtil.getInt(c, StuffColumns.INTENT));
        stuff.setCategory(CursorLoaderUtil.getInt(c, StuffColumns.CATEGORY));
        stuff.setKind(CursorLoaderUtil.getInt(c, StuffColumns.KIND));
        stuff.setEmergency(CursorLoaderUtil.getInt(c, StuffColumns.EMERGENCY));
        stuff.setImportance(CursorLoaderUtil.getInt(c, StuffColumns.IMPORTANCE));
        stuff.setName(CursorLoaderUtil.getString(c, StuffColumns.NAME));
        stuff.setCreationTime(CursorLoaderUtil.getString(c, StuffColumns.CREATION_TIME));
        stuff.setDeadLine(CursorLoaderUtil.getString(c, StuffColumns.DEADLINE));
        stuff.setDescription(CursorLoaderUtil.getString(c, StuffColumns.DESCRIPTION));
        stuff.setStatus(CursorLoaderUtil.getInt(c, StuffColumns.STATUS));
        stuff.setPresure(CursorLoaderUtil.getInt(c, StuffColumns.PRESURE));
        return stuff;
    }
    
    public Uri getUri(){
        return SchedulerProvider.generateUri(TableEnum.STUFF, mId);
    }
    
    public String getDeadDate(){
        if(getDeadLine() == null){
            return null;
        }
        return getDeadLine().split(" ")[0];
    }
    
    public String getDeadTime(){
        if(getDeadLine() == null){
            return null;
        }
        return getDeadLine().split(" ")[1];
    }
    
    public void setDeadDate(String date){
        String oldDate = null;
        String oldTime = null;
        if(mDeadLine == null){
            oldDate = mDeadLine.split(" ")[0];
            oldTime = mDeadLine.split(" ")[1];
        }
        if(oldTime == null || oldDate.isEmpty()){
            mDeadLine = date;
        }else{
            mDeadLine = date + " " + oldTime;
        }
    }
    
    public void setDeadTime(String time){
        String oldDate = null;
        String oldTime = null;
        if(mDeadLine == null){
            oldDate = mDeadLine.split(" ")[0];
            oldTime = mDeadLine.split(" ")[1];
        }
        if(oldTime == null || oldDate.isEmpty()){
            mDeadLine = time;
        }else{
            mDeadLine = oldDate + " " + time;
        }
    }
    
    public Date getDeadLineDateObj(){
        return mDeadLineDateObj;
    }
    
    public void setDeadLineDateObj(Date deadline){
        mDeadLineDateObj = deadline;
        mDeadLine = FORMAT.format(deadline);
    }
}
