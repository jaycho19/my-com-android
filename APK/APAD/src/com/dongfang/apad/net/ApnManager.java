package com.dongfang.apad.net;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.dongfang.apad.util.ULog;

/**
 * 
 * @author dongfang
 * 
 */
public class ApnManager {
	private static final String		TAG					= "ApnManager";

	// 当前移动网络APN数据
	public static final String		PREFERRED_APN_URI	= "content://telephony/carriers/preferapn";
	// 所有移动网络APN数据
	public static final String		APN_TABLE_URI		= "content://telephony/carriers";

	// WIFI
	public static final String		WIFI				= "WIFI";
	// 电信
	public static final String		CTWAP				= "CTWAP";
	public static final String		CTNET				= "CTNET";

	private static final String[]	PROJECTION			= { APN.ID, APN.USER, APN.NUMERIC, APN.MCC, APN.MNC, APN.PROXY };

	private static final Uri		PREFERAPN_URI;
	private Context					context;

	// 当前网络连接APN数据
	private String[]				APNDATA				= new String[PROJECTION.length];

	static {
		PREFERAPN_URI = Uri.parse(PREFERRED_APN_URI);
	}

	public ApnManager(Context context) {
		this.context = context;
		try {
			initApnData();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 初始化数组APNDATA数据，保存了PROJECTION数组中的字段数据 因为4.2的手机获取不到Apn权限
	 */
	private void initApnData() throws Exception {
		Uri uri = Uri.parse(PREFERRED_APN_URI);
		Cursor cursor = context.getContentResolver().query(uri, PROJECTION, null, null, null);

		if (null != cursor && cursor.moveToFirst()) {
			APNDATA[0] = cursor.getString(cursor.getColumnIndex(APN.ID));
			APNDATA[1] = cursor.getString(cursor.getColumnIndex(APN.USER));
			APNDATA[2] = cursor.getString(cursor.getColumnIndex(APN.NUMERIC));
			APNDATA[3] = cursor.getString(cursor.getColumnIndex(APN.MCC));
			APNDATA[4] = cursor.getString(cursor.getColumnIndex(APN.MNC));
			APNDATA[5] = cursor.getString(cursor.getColumnIndex(APN.PROXY));
			for (int i = 0; i < 6; i++) {
				ULog.i(TAG, "initApnData APNDATA = " + APNDATA[i]);
			}
		}
		if (cursor != null) {
			cursor.close();
		}
	}

	/**
	 * 获取当前APN网络类型.
	 * 
	 * @return 如果是电信网络 返回 {@value #_CTWAP} 或者 {@value #_CTNET} 否则 返回
	 *         由NUMERIC和PROXY组成的 String( NUMERIC + "|" + PROXY )
	 * */
	public String getCurrentAPNType() {
		ConnectManager connectmanager = new ConnectManager(context);
		try {
			if (connectmanager.isWIFI())
				return WIFI;

			if (!TextUtils.isEmpty(APNDATA[1]) && APNDATA[1].contains("ctwap")) {
				ULog.i(TAG, "getCurrentAPNType = " + CTWAP);
				return CTWAP;
			}

			if (!TextUtils.isEmpty(APNDATA[1]) && APNDATA[1].contains("ctnet")) {
				ULog.i(TAG, "getCurrentAPNType = " + CTNET);
				return CTNET;
			}

			if (CTWAP.equals(connectmanager.getExtraInfo())) {
				ULog.i(TAG, "getCurrentAPNType = " + CTWAP);
				return CTWAP;
			}

			if (CTNET.equals(connectmanager.getExtraInfo())) {
				ULog.i(TAG, "getCurrentAPNType = " + CTNET);
				return CTNET;
			}

			if (TextUtils.isEmpty(APNDATA[2]) && !TextUtils.isEmpty(APNDATA[3] + APNDATA[4] + APNDATA[5])) {
				ULog.i(TAG, "getCurrentAPNType = " + APNDATA[3] + APNDATA[4] + "|" + APNDATA[5]);
				return APNDATA[3] + APNDATA[4] + "|" + APNDATA[5];
			}
		} catch (Exception e) {
			return "null|null";
		}
		if (!TextUtils.isEmpty(APNDATA[2] + APNDATA[5])) {
			ULog.i(TAG, "getCurrentAPNType = " + APNDATA[2] + "|" + APNDATA[5]);
			return APNDATA[2] + "|" + APNDATA[5];
		}

		return "3G";
	}

	/** 判断是否是 CTWAP网络 */
	public boolean isCTWAP() {
		ULog.d(TAG, "isCTWAP");
		if (ConnectManager.getConnectManager(context).isMOBILE() && CTWAP.equals(getCurrentAPNType())) {
			return true;
		}
		return false;
	}

	/** 判断是否是CTNET网络 */
	public boolean isCTNET() {
		if (ConnectManager.getConnectManager(context).isMOBILE() && CTNET.equals(getCurrentAPNType())) {
			return true;
		}
		return false;
	}

	class APN {
		public static final String	ID			= "_id";
		public static final String	NAME		= "name";
		public static final String	NUMERIC		= "numeric";
		public static final String	MCC			= "mcc";
		public static final String	MNC			= "mnc";
		public static final String	APN			= "apn";
		public static final String	USER		= "user";
		public static final String	SERVER		= "server";
		public static final String	PASSWORD	= "password";
		public static final String	PROXY		= "proxy";
		public static final String	PORT		= "port";
		public static final String	MMSPROXY	= "mmsproxy";
		public static final String	MMSPORT		= "mmsport";
		public static final String	MMSC		= "mmsc";
		public static final String	AUTHTYPE	= "authtype";
		public static final String	TYPE		= "type";
		public static final String	CURRENT		= "current";
		public static final String	ISDEFAULT	= "isdefault";
		public static final String	DIALNUMBER	= "dialnumber";
	}

}