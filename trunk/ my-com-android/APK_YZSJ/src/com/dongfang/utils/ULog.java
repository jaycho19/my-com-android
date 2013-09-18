package com.dongfang.utils;

/** @author dongfang 2013-4-17 */
public class ULog {

	public static final Boolean	ISDUG	= true;

	public static void i(String tag, String msg) {
		if (ISDUG == true) {
			android.util.Log.i(tag, msg);
		}
	}

	public static void v(String tag, String msg) {
		if (ISDUG == true) {
			android.util.Log.v(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (ISDUG == true) {
			android.util.Log.d(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (ISDUG == true) {
			android.util.Log.w(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (ISDUG == true) {
			android.util.Log.e(tag, msg == null ? "ERROR" : msg);
		}

	}
}
