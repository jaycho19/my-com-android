package com.dongfang.apad.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dongfang.apad.OnUpdateDataListener;
import com.dongfang.apad.param.ComParams;
import com.dongfang.apad.util.ULog;

/**
 * 
 * @author dongfang
 * 
 */
public class UpdateDataReceiver extends BroadcastReceiver {
	public static final String	TAG	= UpdateDataReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		ULog.d(TAG, "Action = " + intent.getAction());
		if (null != onUpdateDataListener && intent.getAction().equals(context.getPackageName() + "." + TAG)
				&& 0 != intent.getIntExtra(ComParams.BROADCAST_HANDLER_ACTION_ID, 0)) {

//			ULog.d(TAG, "id = " + intent.getIntExtra(ComParams.BROADCAST_HANDLER_ACTION_ID, 0));

			String des = intent.getStringExtra(ComParams.BROADCAST_HANDLER_DES);
			Bundle data = intent.getExtras();

			switch (intent.getIntExtra(ComParams.BROADCAST_HANDLER_ACTION_ID, 0)) {
			case ComParams.HANDLER_SOCKET_CONNECT_CARD:
				onUpdateDataListener.onSocketConnectCard(des, data);
				break;
			case ComParams.HANDLER_SOCKET_GET_CARD_ID:
				onUpdateDataListener.onGetCardId(des, data);
				break;
			case ComParams.HANDLER_SOCKET_GET_USER_INFO:
				onUpdateDataListener.onGetUserInfo(des, data);
				break;
			case ComParams.HANDLER_SOCKET_CONNECT_TEST_ZKT:
				onUpdateDataListener.onSocketConnectTestZKT(des, data);
				break;
			case ComParams.HANDLER_SOCKET_GET_TEST_ZKT_RESTART:
				onUpdateDataListener.onTestZKTRestarted(des, data);
				break;
			case ComParams.HANDLER_SOCKET_GET_TEST_ZKT_RESULT:
				onUpdateDataListener.onGetTestZKTResult(des, data);
				break;
			default:
				break;
			}
		}

	}

	private OnUpdateDataListener	onUpdateDataListener;

	public void setOnUpdateDataListener(OnUpdateDataListener onUpdateDataListener2) {
		this.onUpdateDataListener = onUpdateDataListener2;
	}

}
