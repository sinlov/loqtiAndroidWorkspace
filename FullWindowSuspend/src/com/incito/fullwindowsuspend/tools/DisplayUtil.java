/*
 * Copyright (c) 2012, Incito Corporation, All Rights Reserved
 */
package com.incito.fullwindowsuspend.tools;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @description 
 * @author   tianran
 * @createDate 2015年2月17日
 * @version  1.0
 */
public class DisplayUtil {
	private static DisplayUtil displayUtil;
	private static Context context;
	private static int width;
	private static int height;
	private static int widthPixels;
	private static int heightPixels;
	private static int densityDpi;
	private static float density;
	private static float scaledDensity;
	private static float xdpi;
	private static float ydpi;
	
	private DisplayUtil() {
	}
	/**
	 * from context to builder DisplayUtil instance.
	 * @description 
	 * @author   tianran
	 * @createDate 2015年2月17日
	 * @param context
	 * @return
	 */
	public static void Builder (Context context){
		if (null == displayUtil) {
			displayUtil = new DisplayUtil();
		}
		DisplayUtil.context = context;
		getDisplayMetrics();
	}
	/**
	 * get Display Metrics
	 * @description 
	 * @author   tianran
	 * @createDate 2015年2月17日
	 * @param context
	 */
	private static void getDisplayMetrics(){
		DisplayMetrics metric = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metric);
		width = metric.widthPixels; // 宽度（PX）
		height = metric.heightPixels; // 高度（PX）
		density = metric.density; // 密度（0.75 / 1.0 / 1.5）
		densityDpi = metric.densityDpi; // 密度DPI（120 / 160 / 240）
		widthPixels = metric.widthPixels;
		heightPixels = metric.heightPixels;
		scaledDensity = metric.scaledDensity;
		xdpi = metric.xdpi;
		ydpi = metric.ydpi;
		ydpi = metric.ydpi;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public static int getDensityDpi() {
		return densityDpi;
	}

	public static float getDensity() {
		return density;
	}

	public static int getWidthPixels() {
		return widthPixels;
	}

	public static int getHeightPixels() {
		return heightPixels;
	}

	public static float getScaledDensity() {
		return scaledDensity;
	}

	public static float getXdpi() {
		return xdpi;
	}

	public static float getYdpi() {
		return ydpi;
	}
	
}
