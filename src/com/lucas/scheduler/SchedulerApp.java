package com.lucas.scheduler;

import android.app.Application;

import com.lucas.scheduler.utilities.Log;

public class SchedulerApp extends Application{
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.framwork("Application onCreate START");
		SchedulerModel.getInstance(this);
		Log.framwork("Application onCreate END");
	}
}
