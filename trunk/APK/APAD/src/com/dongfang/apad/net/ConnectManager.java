package com.dongfang.apad.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.text.TextUtils;

import com.dongfang.apad.util.ULog;

/**
 * 
 * @author dongfang
 * 
 */
public class ConnectManager {
	private static final String		TAG	= "ConnectManager";

	public static ConnectManager	connectManager;

	private ConnectivityManager		connectivityManager;
	private NetworkInfo				networkInfo;

	public ConnectManager(Context context) {
		connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		networkInfo = connectivityManager.getActiveNetworkInfo();

		if (null == networkInfo) {
			ULog.d(TAG, "null == networkInfo");
		}
	}

	public static ConnectManager getConnectManager(Context context) {
		if (null == connectManager)
			connectManager = new ConnectManager(context);

		return connectManager;
	}

	public NetworkInfo getNetworkInfo() {
		return networkInfo;
	}

	/** 判断是否有网络 */
	public boolean isNetworkAvailable() {
		networkInfo = connectivityManager.getActiveNetworkInfo();
		ULog.d("nettest", "判断是否有网络   = " + (networkInfo != null && networkInfo.isAvailable()));
		return (networkInfo != null && networkInfo.isAvailable());
	}

	/** 判断网络是否正在建立 */
	public boolean isNetworkConnecting() {
		ULog.d("nettest", "是否正在建立连接     =  " + (networkInfo != null && (networkInfo.getState() == State.CONNECTING)));
		return (networkInfo != null && (networkInfo.getState() == State.CONNECTING));
	}

	/** 判断是否是WIFI网络状态 */
	public boolean isWIFI() {
		if (isNetworkAvailable()) {
			if (networkInfo == null) {
				networkInfo = connectivityManager.getActiveNetworkInfo();
			}
			return (networkInfo.getType() == ConnectivityManager.TYPE_WIFI);
		}

		return false;
	}

	/** 判断是否是MOBILE网络状态 */
	public boolean isMOBILE() {
		if (isNetworkAvailable()) {
			if (networkInfo == null) {
				networkInfo = connectivityManager.getActiveNetworkInfo();
			}
			return (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
		}

		return false;
	}

	/** 获取当前 网络连接名称 WIFI OR MOBILE */
	public String getNetWorkName() {
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		return (info != null) ? info.getTypeName().toUpperCase() : "null";
	}

	/** 获取当前网络的链接方式，WIRI (1) OR MOBILE (0) */
	public int getNetWorkType() {
		return (null != networkInfo) ? networkInfo.getType() : -1;
	}

	/** CTWAP, CTNET , CMNET, 3GWAP等 */
	public String getExtraInfo() {
		// ULog.d(tag, networkInfo.getExtraInfo() );
		return (null != networkInfo && !TextUtils.isEmpty(networkInfo.getExtraInfo())) ? networkInfo.getExtraInfo()
				.toUpperCase() : "null";
	}

}
