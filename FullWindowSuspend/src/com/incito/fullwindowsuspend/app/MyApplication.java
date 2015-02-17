/*
 * Copyright (c) 2012, Incito Corporation, All Rights Reserved
 */
package com.incito.fullwindowsuspend.app;

import com.incito.fullwindowsuspend.tools.DisplayUtil;

import android.app.Application;
import android.util.Log;

/**
 * @description 
 * @author   tianran
 * @createDate 2015年2月17日
 * @version  1.0
 */
public class MyApplication extends Application {
	public static final String TAG = "com.incito.fullwindowsuspend";
	private static MyApplication myAppliaction;
	public MyApplication() {
		super();
		Log.v(TAG, "MyApplaction new!");
		myAppliaction = this;
	}
	public static MyApplication getInstance() {
		if (myAppliaction == null) {
			myAppliaction = new MyApplication();
		}
		return myAppliaction;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		DisplayUtil.Builder(getApplicationContext());
//		Log.v("this", "du.getDensity(): " + DisplayUtil.getDensity());
//		Log.v("this", "du.getDensityDpi(): " + DisplayUtil.getDensityDpi());
//		Log.v("this", "du.getScaledDensity(): " + DisplayUtil.getScaledDensity());
//		Log.v("this", "du.getWidth(): " + DisplayUtil.getWidth());
//		Log.v("this", "du.getHeight(): " + DisplayUtil.getHeight());
//		Log.v("this", "du.getWidthPixels(): " + DisplayUtil.getWidthPixels());
//		Log.v("this", "du.getHeightPixels(): " + DisplayUtil.getHeightPixels());
//		Log.v("this", "du.getXdpi(): " + DisplayUtil.getXdpi());
//		Log.v("this", "du.getYdpi(): " + DisplayUtil.getYdpi());
	}
}
