package com.dongfang.apad;

import android.app.Application;

import com.dongfang.apad.util.ULog;

/** @author dongfang */
public class BaseApplication extends Application {
	public static final String		TAG				= BaseApplication.class.getSimpleName();
	private static BaseApplication	myApplication	= null;

	public static BaseApplication getInstance() {
		if (myApplication == null) {
			myApplication = new BaseApplication();
			return myApplication;
		}
		return myApplication;
	}

	public void onCreate() {
		super.onCreate();
		ULog.i(TAG, "BASE APP-->" + Thread.currentThread().getId());

	}

}
