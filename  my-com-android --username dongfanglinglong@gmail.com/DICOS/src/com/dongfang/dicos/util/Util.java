package com.dongfang.dicos.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.dongfang.dicos.R;

/** 工具类 */
public class Util {
	private static final String	tag	= "Util";

	/** 隐藏键盘 */
	public static void hideInput(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
		}
	}

	/** 推送设置：卡滋美味 */
	public static boolean getPushKZMW(Context context) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		return setConfig.getBoolean(ComParams.SHAREDPREFERENCES_PUSH_KZMW, false);
	}

	/** 推送设置：卡滋美味 */
	public static boolean savePushKZMW(Context context, boolean b) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		return setConfig.edit().putBoolean(ComParams.SHAREDPREFERENCES_PUSH_KZMW, b).commit();
	}

	/** 推送设置：当季活动 */
	public static boolean getPushDJHD(Context context) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		return setConfig.getBoolean(ComParams.SHAREDPREFERENCES_PUSH_DJHD, false);
	}

	/** 推送设置：当季活动 */
	public static boolean savePushDJHD(Context context, boolean b) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		return setConfig.edit().putBoolean(ComParams.SHAREDPREFERENCES_PUSH_DJHD, b).commit();
	}

	/** 获取USER_NO */
	public static String getUserNO(Context context) {
		// 初始化最近一次登录的手机号码
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		return setConfig.getString(ComParams.SHAREDPREFERENCES_USER_NO, "");
	}

	/** 保存USER_NO */
	public static void saveUserNO(Context context, String user_no) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		setConfig.edit().putString(ComParams.SHAREDPREFERENCES_USER_NO, user_no).commit();
	}

	/** 获取TS */
	public static String getTS(Context context) {
		// 初始化最近一次登录的手机号码
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		return setConfig.getString(ComParams.SHAREDPREFERENCES_TS, "");

	}

	/** 保存TS */
	public static void saveTS(Context context, String ts) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		setConfig.edit().putString(ComParams.SHAREDPREFERENCES_TS, ts).commit();
	}

	/** 获取Token */
	public static String getToken(Context context) {
		// 初始化最近一次登录的手机号码
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		return setConfig.getString(ComParams.SHAREDPREFERENCES_TOKEN, "");

	}

	/** 保存Token */
	public static void saveToken(Context context, String token) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		setConfig.edit().putString(ComParams.SHAREDPREFERENCES_TOKEN, token).commit();
	}

	public static String getNickName(Context context) {
		// 初始化最近一次登录的手机号码
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		return setConfig.getString(ComParams.SHAREDPREFERENCES_NICK_NAME, "");

	}

	/** 保存昵称 */
	public static void setNickName(Context context, String nickName) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		setConfig.edit().putString(ComParams.SHAREDPREFERENCES_NICK_NAME, nickName).commit();
	}

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

	/** 退出对话框 */
	public static void showExitDialog(final Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("德克士").setIcon(R.drawable.ic_menu_notifications).setMessage("是否要退出德克士？")// .setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						activity.finish();
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).show();
	}

	/**
	 * 初始化德克士店名，如果名称中没有包含德克士，就在添加上德克士字符串
	 * 
	 * @param name
	 * @return
	 */
	public static String initNameDicos(String name) {
		return name.contains("德克士") ? name : "德克士" + name;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param context
	 * @param filename
	 * @return
	 */
	public static boolean chkFileExist(Context context, String filename) {
		ULog.d(tag, "path = " + context.getCacheDir());
		return (new File(context.getCacheDir(), filename)).exists();
	}

	/**
	 * 返回屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getWindowWidth(Context context) {
		DisplayMetrics dm = new android.util.DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 返回屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getWindowHeight(Context context) {
		DisplayMetrics dm = new android.util.DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	public static boolean isEmail(String email) {
		if (!email.contains("@") || !email.contains("."))
			return false;
		String str = "[a-zA-Z0-9_-]+@\\w+\\.[a-z]+(\\.[a-z]+)?";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

}
