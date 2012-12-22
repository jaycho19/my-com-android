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
import android.widget.Toast;

import com.dongfang.dicos.R;

/** ������ */
public class Util {
	private static final String	tag	= "Util";

	/** ��ȡUSER_NO */
	public static String getUserNO(Context context) {
		// ��ʼ�����һ�ε�¼���ֻ�����
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		return setConfig.getString(ComParams.SHAREDPREFERENCES_USER_NO, "");

	}

	/** ����USER_NO */
	public static void saveUserNO(Context context, String user_no) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		setConfig.edit().putString(ComParams.SHAREDPREFERENCES_USER_NO, user_no).commit();
	}

	/** ��ȡTS */
	public static String getTS(Context context) {
		// ��ʼ�����һ�ε�¼���ֻ�����
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		return setConfig.getString(ComParams.SHAREDPREFERENCES_TS, "");

	}

	/** ����TS */
	public static void saveTS(Context context, String ts) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		setConfig.edit().putString(ComParams.SHAREDPREFERENCES_TS, ts).commit();
	}

	/** ��ȡToken */
	public static String getToken(Context context) {
		// ��ʼ�����һ�ε�¼���ֻ�����
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		return setConfig.getString(ComParams.SHAREDPREFERENCES_TOKEN, "");

	}

	/** ����Token */
	public static void saveToken(Context context, String token) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		setConfig.edit().putString(ComParams.SHAREDPREFERENCES_TOKEN, token).commit();
	}

	public static String getNickName(Context context) {
		// ��ʼ�����һ�ε�¼���ֻ�����
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		return setConfig.getString(ComParams.SHAREDPREFERENCES_NICK_NAME, "");

	}

	/** �����ǳ� */
	public static void setNickName(Context context, String nickName) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		setConfig.edit().putString(ComParams.SHAREDPREFERENCES_NICK_NAME, nickName).commit();
	}

	/** ��ȡ�ֻ����� */
	public static String getPhoneNumber(Context context) {
		// ��ʼ�����һ�ε�¼���ֻ�����
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		return setConfig.getString(ComParams.SHAREDPREFERENCES_PHONE_NUMBER, "");

	}

	/** �����ֻ����� */
	public static void setPhoneNumber(Context context, String phoneNumber) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		setConfig.edit().putString(ComParams.SHAREDPREFERENCES_PHONE_NUMBER, phoneNumber).commit();
	}

	/** ���õ�¼״̬ */
	public static void setLoginStatus(Context context, boolean b) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);
		setConfig.edit().putBoolean(ComParams.SHAREDPREFERENCES_LOGIN_STATUE, b).commit();
	}

	/** �ж��Ƿ��¼�ɹ� */
	public static boolean isLogin(Context context) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);

		boolean b = setConfig.getBoolean(ComParams.SHAREDPREFERENCES_LOGIN_STATUE, false);
		ULog.d(tag, "isLogin = " + b);
		return b;
	}

	/** �ж��Ƿ������� */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isAvailable());

	}

	/** ��ʾ�������ӶԻ��� */
	public static void showDialogSetNetWork(Context context) {
		Toast.makeText(context, "������������...", Toast.LENGTH_LONG).show();

	}

	/** ��ʾ��¼�Ի��� */
	public static void showDialogLogin(Context context) {
		Toast.makeText(context, "���ȵ�¼...", Toast.LENGTH_LONG).show();
	}

	/** ���浱ǰip��ַ */
	public static void saveIpArea(Context context) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);

		setConfig.edit().putString(ComParams.SHAREDPREFERENCES_IP_AREA, ComParams.IPAREA).commit();

		ULog.d(tag, "saveIpArea = " + ComParams.IPAREA);

	}

	/** ��ʼ�� ip��ַ */
	public static void iniIPArea(Context context) {
		SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
				Activity.MODE_PRIVATE);

		ComParams.IPAREA = TextUtils.isEmpty(ComParams.IPAREA) ? setConfig.getString(
				ComParams.SHAREDPREFERENCES_IP_AREA, "") : ComParams.IPAREA;

	}

	/** �˳��Ի��� */
	public static void showExitDialog(final Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("�¿�ʿ").setIcon(R.drawable.ic_menu_notifications).setMessage("�Ƿ�Ҫ�˳��¿�ʿ��")// .setCancelable(false)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						activity.finish();
					}
				}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).show();
	}

	/**
	 * ��ʼ���¿�ʿ���������������û�а����¿�ʿ����������ϵ¿�ʿ�ַ���
	 * 
	 * @param name
	 * @return
	 */
	public static String initNameDicos(String name) {
		return name.contains("�¿�ʿ") ? name : "�¿�ʿ" + name;
	}

	/**
	 * �ж��ļ��Ƿ����
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
	 * ������Ļ���
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
	 * ������Ļ�߶�
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
