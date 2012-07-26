package com.dongfang.dicos.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.Toast;

/** 工具类 */
public class Util {
	private static final String	tag	= "Util";

	/** 获取手机号码 */
	public static String getPhoneNumber(Context context) {
		// 初始化最近一次登录的手机号码
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		return setConfig.getString(ComParams.SHAREDPREFERENCES_PHONE_NUMBER, "");

	}

	/** 保存手机号码 */
	public static void setPhoneNumber(Context context, String phoneNumber) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		setConfig.edit().putString(ComParams.SHAREDPREFERENCES_PHONE_NUMBER, phoneNumber).commit();
	}

	/** 设置登录状态 */
	public static void setLoginStatus(Context context, boolean b) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		setConfig.edit().putBoolean(ComParams.SHAREDPREFERENCES_LOGIN_STATUE, b).commit();
	}

	/** 判断是否登录成功 */
	public static boolean isLogin(Context context) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);

		boolean b = setConfig.getBoolean(ComParams.SHAREDPREFERENCES_LOGIN_STATUE, false);
		ULog.d(tag, "isLogin = " + b);
		return b;
	}

	/** 判断是否有网络 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isAvailable());

	}

	/** 显示网络链接对话框 */
	public static void showDialogSetNetWork(Context context) {
		Toast.makeText(context, "请先链接网络...", Toast.LENGTH_LONG).show();

	}

	/** 显示登录对话框 */
	public static void showDialogLogin(Context context) {
		Toast.makeText(context, "请先登录...", Toast.LENGTH_LONG).show();
	}

	/** 保存当前ip地址 */
	public static void saveIpArea(Context context) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);

		setConfig.edit().putString(ComParams.SHAREDPREFERENCES_IP_AREA, ComParams.IPAREA).commit();

		ULog.d(tag, "saveIpArea = " + ComParams.IPAREA);

	}

	/** 初始化 ip地址 */
	public static void iniIPArea(Context context) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);

		ComParams.IPAREA = TextUtils.isEmpty(ComParams.IPAREA) ? setConfig.getString(
				ComParams.SHAREDPREFERENCES_IP_AREA, "") : ComParams.IPAREA;

	}

}
