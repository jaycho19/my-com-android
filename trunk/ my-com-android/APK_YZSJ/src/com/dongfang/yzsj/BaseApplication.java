package com.dongfang.yzsj;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.dongfang.mediaplayer.constants.Constants;
import com.dongfang.utils.ULog;

/** @author dongfang 该类当下只正对QAS有效，2013-2-1 */
public class BaseApplication extends Application {
	public static final String		TAG					= BaseApplication.class.getSimpleName();
	private static BaseApplication	myApplication		= null;

	/** 行为统计 */
	private int						actionReportCode	= -1;



	public static BaseApplication getInstance() {
		if (myApplication == null) {
			myApplication = new BaseApplication();
			return myApplication;
		}
		return myApplication;
	}

	public void onCreate() {
		super.onCreate();

		myApplication = this;
		getDeviceInfo();
	}

	public void getDeviceInfo() {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);

		if (dm.widthPixels < dm.heightPixels) {
			// 竖屏
			Constants.SCREEN_WIDTH_PORTRAIT = dm.widthPixels;
			Constants.SCREEN_HEIGHT_PORTRAIT = dm.heightPixels;

			Constants.SCREEN_HEIGHT_LANDSCAPE = dm.widthPixels;
			Constants.SCREEN_WIDTH_LANDSCAPE = dm.heightPixels;
		}
		else {
			// 横屏
			Constants.SCREEN_WIDTH_PORTRAIT = dm.heightPixels;
			Constants.SCREEN_HEIGHT_PORTRAIT = dm.widthPixels;

			Constants.SCREEN_HEIGHT_LANDSCAPE = dm.heightPixels;
			Constants.SCREEN_WIDTH_LANDSCAPE = dm.widthPixels;
		}
		Constants.DENSITYDPI = dm.densityDpi;
		Constants.DENSITY = dm.density;
		ULog.i( "setupBaseData, SCREEN_WIDTH = " + dm.widthPixels + ", SCREEN_HEIGHT = " + dm.heightPixels
				+ ", densityDpi = " + dm.densityDpi);
	}

}
